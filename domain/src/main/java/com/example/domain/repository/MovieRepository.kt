package com.example.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.example.domain.MovieApi
import com.example.domain.MovieRemoteMediator
import com.example.domain.database.MoviesDao
import com.example.domain.database.TheMovieDB
import com.example.domain.database.entity.MovieListItem
import com.example.domain.database.entity.PageKeys
import com.example.network.ITheMovieDbApiService
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.hours

/**
 * Middleman between the view model/ui layer and the database/network layers
 */
class MovieRepository(
    private val movieService: ITheMovieDbApiService,
    private val database: TheMovieDB
) {

    private val movieListDao = database.moviesDao()

    /**
     * Gets the list of movies for a certain [MovieApi] as found in [MoviesDao]. If the database
     * doesn't have the matching data, fetches the data from the network using [MovieRemoteMediator]
     *
     * @param api A [MovieApi] that tells the database and network what
     * api (ie: [MovieApi.POPULAR], [MovieApi.LATEST]) that we are interested in.
     *
     *  @return A [Flow] of [PagingData] that contains [MovieListItem]
     */
    @OptIn(ExperimentalPagingApi::class)
    fun getPagedMovieList(api: MovieApi): Flow<PagingData<MovieListItem>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20),
        remoteMediator = MovieRemoteMediator(database, movieService, api, 1.hours),
        pagingSourceFactory = { movieListDao.pagingSource(api.name) }
    ).flow

    /**
     * Gets a list of movies from either the database or network.
     *
     * @param api A [MovieApi] that tells the database and network what api
     * (ie: [MovieApi.POPULAR], [MovieApi.TOP_RATED]) we are interested in
     *
     * @param page What page to fetch.
     *
     * @return A list of [MovieListItem].
     */
    suspend fun getMovieList(api: MovieApi, page: Int): List<MovieListItem> {
        // Check for data from database
        val data = movieListDao.getMovies(api.name, page)
        if (data.isNotEmpty()) return data

        // If no data locally fetch from network
        val movies = movieService.movies(api.toNetworkApi(), page)
            .results.map {
                MovieListItem.fromNetworkObject(it, api.name, page)
            }

        database.withTransaction {
            database.pageDao().insert(PageKeys(api.name, page))
            database.moviesDao().insertAll(movies)
        }

        return movies
    }

    /**
     * Get the movie details
     */
    fun getMovieDetails(movieId: Int) = movieId
}
