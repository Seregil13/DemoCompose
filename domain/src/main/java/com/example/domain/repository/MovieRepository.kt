package com.example.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.example.database.MoviesDatabase
import com.example.database.entity.PagingKeys
import com.example.domain.MovieListPagingSource
import com.example.domain.model.MovieApi
import com.example.domain.model.MovieListItem
import com.example.domain.toDatabaseMovie
import com.example.domain.toDatabaseMovieDetail
import com.example.domain.toDomainMovie
import com.example.domain.toDomainMovieDetail
import com.example.network.ITheMovieDbApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import com.example.database.entity.MovieDetail as DatabaseMovieDetail
import com.example.domain.model.MovieDetailItem as DomainMovieDetail

/**
 * Middleman between the view model/ui layer and the database/network layers
 */
class MovieRepository(
    private val movieService: ITheMovieDbApiService,
    private val database: MoviesDatabase,
) {
    /**
     * Gets the list of movies for a certain [MovieApi] as found in [MoviesDao]. If the database
     * doesn't have the matching data, fetches the data from the network using [MovieListPagingSource]
     *
     * @param api A [MovieApi] that tells the database and network what
     * api (ie: [MovieApi.POPULAR], [MovieApi.LATEST]) that we are interested in.
     *
     *  @return A [Flow] of [PagingData] that contains [MovieListItem]
     */
    @OptIn(ExperimentalPagingApi::class)
    fun getPagedMovieList(api: MovieApi): Flow<PagingData<MovieListItem>> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        MovieListPagingSource(database, movieService, api)
    }
        .flow

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
        val data = database.movieListDao().getMovies(api.name, page)
        if (data.isNotEmpty()) return data.map { it.toDomainMovie() }

        // If no data locally fetch from network
        val movies = movieService.movies(api.toNetworkApi(), page)
            .results.map { it.toDomainMovie(api, page) }

        database.withTransaction {
            database.pagingKeysDao().insert(PagingKeys(api.name, page))
            database.movieListDao().insertAll(movies.map { it.toDatabaseMovie() })
        }

        return movies
    }

    /**
     * Get the movie details
     */
    suspend fun getMovieDetails(
        movieId: Int,
        cacheTimeout: Duration = 1.days
    ): DomainMovieDetail {
        // First check the database for the movie
        val dbDetails: DatabaseMovieDetail? = database.movieDetailsDao().getDetails(movieId)
        // Check if the movie exists and is not stale
        if (dbDetails != null && Clock.System.now() - dbDetails.lastUpdateTime < cacheTimeout)
            return dbDetails.toDomainMovieDetail()

        // Fetch details from the network
        val nDetails: DomainMovieDetail = movieService.movie(movieId).toDomainMovieDetail()

        // Save the movie to the database
        database.withTransaction {
            database.movieDetailsDao().insert(nDetails.toDatabaseMovieDetail())
        }

        return nDetails
    }
}
