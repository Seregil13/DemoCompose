package com.example.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.example.database.MoviesDatabase
import com.example.database.entity.MovieList
import com.example.database.entity.PagingKeys
import com.example.domain.model.MovieApi
import com.example.domain.model.MovieListItem
import com.example.network.ITheMovieDbApiService
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.days

class MovieListPagingSource(
    private val database: MoviesDatabase,
    private val service: ITheMovieDbApiService,
    private val api: MovieApi
) : PagingSource<Int, MovieListItem>() {

    override fun getRefreshKey(state: PagingState<Int, MovieListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListItem> {
        return try {
            val nextPage = params.key ?: STARTING_PAGE_NUMBER
            isPageInDatabase(nextPage) ?: fetchPageFromNetwork(nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    /**
     * Checks if the given page has been cached in the [MoviesDatabase]
     *
     * @param pageNumber The page to check the database for.
     *
     * @return A LoadResult containing the data or null if the data isn't in the database or is stale
     */
    private suspend fun isPageInDatabase(
        pageNumber: Int
    ): LoadResult<Int, MovieListItem>? {
        val dbMovies: List<MovieList> = database.movieListDao().getMovies(api.name, pageNumber)
        if (dbMovies.isNotEmpty()) {
            if (!isPageStale(dbMovies)) {
                return LoadResult.Page(
                    data = dbMovies.map { it.toDomainMovie() },
                    prevKey = if (pageNumber <= 1) null else pageNumber - 1,
                    nextKey = pageNumber + 1
                )
            } else {
                database.withTransaction {
                    database.movieListDao().deleteByPage(pageNumber)
                }
            }
        }

        return null
    }

    /**
     * Get the page from the network.
     */
    private suspend fun fetchPageFromNetwork(pageNumber: Int): LoadResult<Int, MovieListItem> {
        val networkMoviesResponse = service.movies(
            movieApi = api.toNetworkApi(),
            page = pageNumber
        )

        if (networkMoviesResponse.results.isEmpty()) {
            return LoadResult.Error(EmptyResponseException("Empty results in response."))
        }

        val domainModels = networkMoviesResponse.results.map { it.toDomainMovie(api, pageNumber) }
        val nextPage = if (pageNumber <= networkMoviesResponse.totalPages) pageNumber + 1 else null

        database.withTransaction {
            database.pagingKeysDao().insert(PagingKeys(api.name, nextPage))
            database.movieListDao().insertAll(domainModels.map { it.toDatabaseMovie() })
        }

        return LoadResult.Page(
            data = domainModels,
            prevKey = if (pageNumber <= 1) null else pageNumber - 1,
            nextKey = nextPage?.plus(1)
        )
    }

    private fun isPageStale(movies: List<MovieList>): Boolean {
        // Find the last updated time from the database
        val lastUpdated: MovieList? = movies.maxByOrNull { it.lastUpdateTime }

        // If the last updated time is null, we don't have any data so make sure initial data
        // is fetched from the network
        if (lastUpdated?.lastUpdateTime == null) return false

        // If the last update time is more recent than the cache timeout, skip the initial refresh
        // otherwise does the initial refresh which will delete local data and fetch fresh data
        return Clock.System.now() - lastUpdated.lastUpdateTime > cacheTimeout
    }

    companion object {
        private const val STARTING_PAGE_NUMBER = 1
        private val cacheTimeout = 1.days
    }
}
