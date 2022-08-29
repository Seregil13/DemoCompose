package com.example.democompose.framework

import com.example.core.data.MovieListRemoteDataSource
import com.example.core.data.Outcome
import com.example.core.domain.MovieApi
import com.example.core.domain.MovieListItem
import com.example.core.domain.PagedApiResponse
import com.example.network.ITheMovieDbApiService
import com.example.network.response.MovieListItemResponse
import com.example.network.response.PagedResponse

class MovieListNetworkDataSource(
    private val service: ITheMovieDbApiService
): MovieListRemoteDataSource {

    override suspend fun getMovies(api: MovieApi, page: Int): Outcome<PagedApiResponse<MovieListItem>> {
        return try {
            val movies: PagedResponse<MovieListItemResponse> = service.movies(movieApi = api.toNetworkApi(), page = page)
            Outcome.Success(
                PagedApiResponse(
                    page = movies.page,
                    results = movies.results.map { it.toCoreMovie() },
                    totalPages = movies.totalPages,
                    totalResults = movies.totalResults
                )
            )
        } catch (e: Exception) {
            // TODO - catch specific exceptions not blanket
            Outcome.Error("Error from network", e)
        }
    }

}