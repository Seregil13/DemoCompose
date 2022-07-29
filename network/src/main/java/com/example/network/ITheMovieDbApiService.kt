package com.example.network

import com.example.network.response.ConfigurationResponse
import com.example.network.response.MovieListItemResponse
import com.example.network.response.MovieResponse
import com.example.network.response.PagedResponse

interface ITheMovieDbApiService {
    suspend fun configuration(): ConfigurationResponse
    suspend fun movie(movieId: Int): MovieResponse
    suspend fun popularMovies(page: Int? = 1): PagedResponse<MovieListItemResponse>
}
