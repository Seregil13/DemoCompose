package com.example.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.domain.database.TheMovieDB
import com.example.domain.database.entity.MovieListItem
import com.example.network.ITheMovieDbApiService
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.hours

class MovieListRepository(
    private val movieService: ITheMovieDbApiService,
    private val database: TheMovieDB
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getMovieList(api: MovieApi): Flow<PagingData<MovieListItem>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20),
        remoteMediator = MovieRemoteMediator(database, movieService, api, 1.hours),
        pagingSourceFactory = { database.moviesDao().pagingSource(api.name) }
    ).flow
}
