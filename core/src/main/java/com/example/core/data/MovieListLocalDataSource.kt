package com.example.core.data

import com.example.core.domain.MovieApi
import com.example.core.domain.MovieListItem
import kotlin.time.Duration

interface MovieListLocalDataSource {
    suspend fun getMovies(api: MovieApi, page: Int): Outcome<List<MovieListItem>>
    suspend fun saveMovies(api: MovieApi, page: Int, movies: List<MovieListItem>)
    suspend fun deleteMovies(api: MovieApi, page: Int)
    suspend fun isPageStale(api: MovieApi, page: Int, cacheTimeout: Duration): Boolean
    suspend fun getMoviesIfNotStale(api: MovieApi, page: Int, timeout: Duration): Outcome<List<MovieListItem>>
}

sealed class Outcome<out Data : Any> {
    data class Success<out T : Any>(val value: T) : Outcome<T>()
    data class Error(val reason: String, val cause: Exception? = null) : Outcome<Nothing>()
}