package com.example.domain

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.example.domain.database.TheMovieDB
import com.example.domain.database.entity.MovieListItem
import com.example.domain.database.entity.PageKeys
import com.example.network.ITheMovieDbApiService
import com.example.network.KtorClient
import com.example.network.TheMovieDbApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDate
import kotlinx.serialization.encodeToString
import timber.log.Timber
import java.lang.Integer.max
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

class MoviePagingSource(
    private val movieService: ITheMovieDbApiService
) : PagingSource<Int, MovieListItem>() {

    override fun getRefreshKey(state: PagingState<Int, MovieListItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListItem> {
        return try {
            val page = params.key ?: STARTING_KEY
            val response = withContext(Dispatchers.IO) {
                movieService.popularMovies(page)
            }

            Timber.tag("MoviePagingSource")
                .d("load: " + response.page + " of " + response.totalPages + " || " + response.results.size)

            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (response.page < response.totalPages) page + 1 else null

            val lrp = LoadResult.Page(
                data = response.results.map {
                    MovieListItem(
                        id = it.id,
                        title = it.title,
                        releaseDate = if (it.releaseDate.isNotBlank()) it.releaseDate.toLocalDate() else null,
                        popularity = it.popularity,
                        posterUrl = it.posterPath,
                        api = "popular",
                        lastUpdateTime = Clock.System.now()
                    )
                },
                prevKey = prevKey,
                nextKey = nextKey
            )

            Log.d("MoviePagingSource", "load: ${lrp.nextKey} ${lrp.prevKey}")

            lrp
        } catch (e: Exception) {
            Log.e("MoviePagingSource", "load: Error Loading ${e.message}")
            LoadResult.Error(e)
        }
    }

    /**
     * Make sure the page is never less that [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

    companion object {
        private const val STARTING_KEY = 1
    }

}

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val database: TheMovieDB,
    private val movieService: ITheMovieDbApiService,
    private val api: TheMovieDbApiService.MovieApi
) : RemoteMediator<Int, MovieListItem>() {

    private val timber = Timber.tag("MovieRemoteMediator")

    init {
        timber.d("Initializing")
    }

    private val pageDao = database.pageDao()
    private val moviesDao = database.moviesDao()

    override suspend fun initialize(): InitializeAction {
        timber.d("initialize()")

        val lastUpdated = moviesDao.getMovies(api.name)
            .sortedBy { it.lastUpdateTime }
            .lastOrNull()

        timber.d("initialize() lastUpdated: ${KtorClient.json.encodeToString(lastUpdated)}")

        if (lastUpdated?.lastUpdateTime == null) return InitializeAction.LAUNCH_INITIAL_REFRESH

        val t: Duration = Clock.System.now() - lastUpdated.lastUpdateTime
        val cacheTimeout = 1.days

        timber.d("initialize() $t >= $cacheTimeout")

        return if (t >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieListItem>
    ): MediatorResult {
        try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
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

            timber.d("page to fetch - $loadKey")

            val response = withContext(Dispatchers.IO) {
                movieService.popularMovies(loadKey)
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pageDao.deleteByApi(api.name)
                    moviesDao.clearAll(api.name)
                }

                val nextKey =
                    if (loadKey != null && response.page < response.totalPages) loadKey + 1 else null

                val movies = response.results.map {
                    MovieListItem(
                        id = it.id,
                        title = it.title,
                        releaseDate = if (it.releaseDate.isNotBlank()) it.releaseDate.toLocalDate() else null,
                        popularity = it.popularity,
                        posterUrl = it.posterPath,
                        api = api.name,
                        lastUpdateTime = Clock.System.now()
                    )
                }
                timber.d("Database insertion")
                database.withTransaction {
                    pageDao.insert(PageKeys(api.name, nextKey))
                    moviesDao.insertAll(movies)
                }
                timber.d("Done inserting database")
            }

            return MediatorResult.Success(true)
        } catch (e: Exception) {
            timber.e(e)
            return MediatorResult.Error(e)
        }
    }
}
