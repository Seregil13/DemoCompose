package com.example.network

import com.example.network.response.ConfigurationResponse
import com.example.network.response.MovieListItemResponse
import com.example.network.response.MovieResponse
import com.example.network.response.PagedResponse
import io.ktor.client.call.*
import io.ktor.client.request.*

object TheMovieDbApiService : ITheMovieDbApiService {
    override suspend fun configuration(): ConfigurationResponse {
        return KtorClient.client
            .get("configuration")
            .body()
    }

    override suspend fun movie(movieId: Int): MovieResponse {
        return KtorClient.client
            .get("movie/$movieId")
            .body()
    }

    override suspend fun movies(
        movieApi: MovieApi,
        page: Int?,
        region: String?,
        language: String?
    ): PagedResponse<MovieListItemResponse> {
        return KtorClient.client
            .get(movieApi.path) {
                url {
                    parameters.append("page", "${page ?: 1}")
                }
            }
            .body()
    }
}
