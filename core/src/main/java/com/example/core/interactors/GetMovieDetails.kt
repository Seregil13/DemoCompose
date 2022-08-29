package com.example.core.interactors

import com.example.core.data.MovieDetailsRepository
import com.example.core.data.Outcome
import com.example.core.domain.MovieDetailItem

class GetMovieDetails(private val repository: MovieDetailsRepository) {
    suspend operator fun invoke(movieId: Int): Outcome<MovieDetailItem> {
        return when (val localMovie = repository.getLocalMovieIfNotStale(movieId)) {
            is Outcome.Success -> {
                localMovie
            }
            is Outcome.Error -> {
                // TODO - add some kind of logging/analytics
                val remoteMovie = repository.getRemoteMovie(movieId)

                if (remoteMovie is Outcome.Success) {
                    repository.saveLocalMovie(remoteMovie.value)
                }

                remoteMovie
            }
        }
    }
}
