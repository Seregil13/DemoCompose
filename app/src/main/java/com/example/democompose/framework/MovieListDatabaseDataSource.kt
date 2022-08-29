package com.example.democompose.framework

import com.example.core.data.MovieListLocalDataSource
import com.example.core.data.Outcome
import com.example.core.domain.MovieApi
import com.example.core.domain.MovieListItem
import com.example.database.MoviesDatabase
import com.example.database.dao.MovieListDao
import com.example.database.entity.MovieList
import kotlinx.datetime.Clock
import kotlin.time.Duration

class MovieListDatabaseDataSource(
    database: MoviesDatabase
): MovieListLocalDataSource {

    private val movieListDao: MovieListDao = database.movieListDao()

    override suspend fun getMovies(api: MovieApi, page: Int): Outcome<List<MovieListItem>> {
        val movies = movieListDao.getMovies(api.name, page)
            .map { it.toCoreMovie() }

        return if (movies.isNotEmpty()) {
            Outcome.Success(movies)
        } else {
            Outcome.Error(reason = "Something", Exception())
        }

    }

    override suspend fun saveMovies(api: MovieApi, page: Int, movies: List<MovieListItem>) {
        movieListDao.insertAll(movies.map { it.toDatabaseMovie(api, page) })
    }

    override suspend fun deleteMovies(api: MovieApi, page: Int) {
        movieListDao.deleteByPage(api.name, page)
    }

    override suspend fun isPageStale(api: MovieApi, page: Int, cacheTimeout: Duration): Boolean {
        val movies = movieListDao.getMovies(api.name, page)
        return movies.isEmpty() || Clock.System.now() - movies.first().lastUpdateTime > cacheTimeout
    }

    override suspend fun getMoviesIfNotStale(
        api: MovieApi,
        page: Int,
        timeout: Duration
    ): Outcome<List<MovieListItem>> {
        val movies = movieListDao.getMovies(api.name, page)
        return when {
            movies.isEmpty() -> Outcome.Error("Not in database.")
            movies.first().isStale(timeout) -> Outcome.Error("Database data is stale.")
            else -> Outcome.Success(movies.map { it.toCoreMovie() })
        }
    }

    private fun MovieList.isStale(timeout: Duration) = Clock.System.now() - this.lastUpdateTime > timeout
}