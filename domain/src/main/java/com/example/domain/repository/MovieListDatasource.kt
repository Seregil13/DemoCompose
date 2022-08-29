package com.example.domain.repository

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
import com.example.domain.toDomainMovie
import com.example.network.ITheMovieDbApiService
import kotlinx.coroutines.flow.Flow

interface MovieListDatasource {

    suspend fun getMoviePagingSource(api: MovieApi): Flow<PagingData<MovieListItem>>
    suspend fun getMovieList(api: MovieApi, page: Int): List<MovieListItem>

}

class MoviesDataSource(
    private val database: MoviesDatabase,
    private val service: ITheMovieDbApiService
): MovieListDatasource {

    override suspend fun getMoviePagingSource(api: MovieApi): Flow<PagingData<MovieListItem>> {
        return Pager(
            PagingConfig(20)
        ) { MovieListPagingSource(database, service, api) }
            .flow
    }

    override suspend fun getMovieList(api: MovieApi, page: Int): List<MovieListItem> {
        val data = database.movieListDao().getMovies(api.name, page)
        if (data.isNotEmpty()) return data.map { it.toDomainMovie() }

        // If no data locally fetch from network
        val movies = service.movies(api.toNetworkApi(), page)
            .results.map { it.toDomainMovie(api, page) }

        database.withTransaction {
            database.pagingKeysDao().insert(PagingKeys(api.name, page))
            database.movieListDao().insertAll(movies.map { it.toDatabaseMovie() })
        }

        return movies
    }
}