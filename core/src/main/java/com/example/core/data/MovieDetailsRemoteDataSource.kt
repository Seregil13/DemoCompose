package com.example.core.data

import com.example.core.domain.MovieDetailItem

interface MovieDetailsRemoteDataSource {
    suspend fun getMovie(movieId: Int): Outcome<MovieDetailItem>
}