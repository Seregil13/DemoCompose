package com.example.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.domain.database.TheMovieDB
import com.example.domain.database.entity.MovieListItem
import com.example.domain.database.entity.PageKeys
import com.example.network.ITheMovieDbApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import timber.log.Timber
import kotlin.time.Duration

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val database: TheMovieDB,
    private val movieService: ITheMovieDbApiService,
    private val api: MovieApi,
    private val cacheTimeout: Duration
) : RemoteMediator<Int, MovieListItem>() {

    private val pageDao = database.pageDao()
    private val moviesDao = database.moviesDao()

    override suspend fun initialize(): InitializeAction {
        // Find the last updated time from the database
        val lastUpdated = moviesDao.getMovies(api.name)
            .sortedBy { it.lastUpdateTime }
            .lastOrNull()

        // If the last updated time is null, we don't have any data so make sure initial data
        // is fetched from the network
        if (lastUpdated?.lastUpdateTime == null) return InitializeAction.LAUNCH_INITIAL_REFRESH

        // If the last update time is more recent than the cache timeout, skip the initial refresh
        // otherwise does the initial refresh which will delete local data and fetch fresh data
        return if (Clock.System.now() - lastUpdated.lastUpdateTime <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieListItem>
    ): MediatorResult {
        Timber.d(state.toString())
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> STARTING_PAGE_NUMBER
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val (_, nextPage) = database.withTransaction {
                        pageDao.remotePageForApi(api.name)
                    }

                    if (nextPage == null) {
                        return MediatorResult.Success(true)
                    }

                    nextPage
                }
            }

            val response = withContext(Dispatchers.IO) {
                movieService.movies(page = loadKey, movieApi = api.toNetworkApi())
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pageDao.deleteByApi(api.name)
                    moviesDao.clearAll(api.name)
                }
            }

            val nextKey = if (response.page < response.totalPages) response.page + 1 else null

            val movies = response.results.map {
                MovieListItem.fromNetworkObject(it, api.name, response.page)
            }

            database.withTransaction {
                pageDao.insert(PageKeys(api.name, nextKey))
                moviesDao.insertAll(movies)
            }

            return MediatorResult.Success(false)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_PAGE_NUMBER = 1
    }
}
