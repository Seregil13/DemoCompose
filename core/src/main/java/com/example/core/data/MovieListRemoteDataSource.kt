package com.example.core.data

import com.example.core.domain.MovieApi
import com.example.core.domain.MovieListItem
import com.example.core.domain.PagedApiResponse

interface MovieListRemoteDataSource {
    suspend fun getMovies(api: MovieApi, page: Int): Outcome<PagedApiResponse<MovieListItem>>
}