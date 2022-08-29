package com.example.core.interactors

import com.example.core.data.MovieListRepository
import com.example.core.data.Outcome
import com.example.core.domain.MovieApi
import com.example.core.domain.MovieListItem
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

class GetMovieList(private val repository: MovieListRepository) {
    suspend operator fun invoke(movieApi: MovieApi, page: Int, cacheTimeout: Duration = 7.days): Outcome<List<MovieListItem>> {
        return when (val localMoviesOutcome = repository.getLocalMoviesOrDeleteIfStale(movieApi, page, cacheTimeout)) {
            is Outcome.Error -> {
                when (val remoteMovies = repository.getRemoteMovies(movieApi, page)) {
                    is Outcome.Success -> {
                        repository.saveLocalMovies(movieApi, page, remoteMovies.value.results)
                        Outcome.Success(remoteMovies.value.results)
                    }
                    is Outcome.Error -> {
                        Outcome.Error(remoteMovies.reason, remoteMovies.cause)
                    }
                }
            }
            is Outcome.Success -> {
                return localMoviesOutcome
            }
        }
    }
}
