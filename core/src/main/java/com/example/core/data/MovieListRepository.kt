package com.example.core.data

import com.example.core.domain.MovieApi
import com.example.core.domain.MovieListItem
import com.example.core.domain.PagedApiResponse
import kotlin.time.Duration

class MovieListRepository(
    private val localDataSource: MovieListLocalDataSource,
    private val remoteDataSource: MovieListRemoteDataSource
) {
    suspend fun getLocalMovies(api: MovieApi, page: Int): Outcome<List<MovieListItem>> {
        return localDataSource.getMovies(api, page)
    }

    suspend fun saveLocalMovies(api: MovieApi, page: Int, movies: List<MovieListItem>) {
        localDataSource.saveMovies(api, page, movies)
    }

    suspend fun getLocalMoviesOrDeleteIfStale(api: MovieApi, page: Int, timeout: Duration): Outcome<List<MovieListItem>> {
        return localDataSource.getMoviesIfNotStale(api, page, timeout)
    }

    suspend fun getRemoteMovies(api: MovieApi, page: Int): Outcome<PagedApiResponse<MovieListItem>> {
        return remoteDataSource.getMovies(api, page)
    }
    suspend fun isLocalPageStale(api: MovieApi, page: Int, cacheTimeout: Duration): Boolean {
        return localDataSource.isPageStale(api, page, cacheTimeout)
    }

    suspend fun deleteLocalPage(api: MovieApi, page: Int) {
        localDataSource.deleteMovies(api, page)
    }
}