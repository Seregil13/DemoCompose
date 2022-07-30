package com.example.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.database.entity.MovieListItem
import com.example.network.ITheMovieDbApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDate
import timber.log.Timber

@Suppress("unused")
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
                movieService.movies(MovieApi.POPULAR.toNetworkApi(), page)
            }

            Timber.tag("MoviePagingSource")
                .d("load:  ${response.page} of  ${response.totalPages} || ${response.results.size}")

            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (response.page < response.totalPages) page + 1 else null

            val lrp = LoadResult.Page(
                data = response.results.map {
                    MovieListItem(
                        movieId = it.id,
                        title = it.title,
                        releaseDate = if (it.releaseDate.isNotBlank()) it.releaseDate.toLocalDate() else null,
                        popularity = it.popularity,
                        posterUrl = it.posterPath,
                        api = "popular",
                        page = page,
                        lastUpdateTime = Clock.System.now()
                    )
                },
                prevKey = prevKey,
                nextKey = nextKey
            )
            lrp
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    /**
     * Make sure the page is never less that [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = Integer.max(STARTING_KEY, key)

    companion object {
        private const val STARTING_KEY = 1
    }

}