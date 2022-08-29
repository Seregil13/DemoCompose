package com.example.core.data

import com.example.core.domain.MovieDetailItem

interface MovieDetailsLocalDataSource {
    suspend fun getMovie(movieId: Int): Outcome<MovieDetailItem>
    suspend fun saveMovie(movie: MovieDetailItem)
    suspend fun deleteMovie(movieId: Int)
    suspend fun isMovieStale(movieId: Int): Boolean
    suspend fun getOrDeleteMovieIfStale(movieId: Int): Outcome<MovieDetailItem>
}