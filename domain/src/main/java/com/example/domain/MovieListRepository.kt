package com.example.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.domain.database.TheMovieDB
import com.example.domain.database.entity.MovieListItem
import com.example.network.ITheMovieDbApiService
import com.example.network.TheMovieDbApiService
import kotlinx.coroutines.flow.Flow

class MovieListRepository(
    private val movieService: ITheMovieDbApiService,
    private val database: TheMovieDB
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getMovieList(api: MovieApi): Flow<PagingData<MovieListItem>> = Pager<Int, MovieListItem>(
        config = PagingConfig(pageSize = 20),
        remoteMediator = MovieRemoteMediator(database, movieService, api.toNetworkApi()),
        pagingSourceFactory = { database.moviesDao().pagingSource(api.toNetworkApi().name) }
    ).flow
}

enum class MovieApi {
    POPULAR;


    internal fun toNetworkApi(): TheMovieDbApiService.MovieApi = when(this) {
        POPULAR -> TheMovieDbApiService.MovieApi.POPULAR
    }
}