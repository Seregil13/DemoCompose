package com.example.core.data

import com.example.core.domain.MovieDetailItem

class MovieDetailsRepository(
    private val localDataSource: MovieDetailsLocalDataSource,
    private val remoteDataSource: MovieDetailsRemoteDataSource
) {

    suspend fun getLocalMovie(movieId: Int): Outcome<MovieDetailItem> {
        return localDataSource.getMovie(movieId)
    }

    suspend fun isLocalMovieStale(movieId: Int): Boolean {
        return localDataSource.isMovieStale(movieId)
    }

    suspend fun getLocalMovieIfNotStale(movieId: Int): Outcome<MovieDetailItem> {
        return localDataSource.getOrDeleteMovieIfStale(movieId)
    }

    suspend fun saveLocalMovie(movie: MovieDetailItem) {
        localDataSource.saveMovie(movie)
    }

    suspend fun getRemoteMovie(movieId: Int): Outcome<MovieDetailItem> {
        return remoteDataSource.getMovie(movieId)
    }

    suspend fun deleteLocalMovie(movieId: Int) {
        localDataSource.deleteMovie(movieId)
    }
}