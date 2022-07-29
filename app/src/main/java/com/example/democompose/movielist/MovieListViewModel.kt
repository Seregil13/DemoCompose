package com.example.democompose.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.MovieApi
import com.example.domain.MovieListRepository
import com.example.domain.database.entity.MovieListItem
import kotlinx.coroutines.flow.Flow

class MovieListViewModel(
    private val movieListRepository: MovieListRepository,
): ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val movies: Flow<PagingData<MovieListItem>> = movieListRepository.getMovieList(MovieApi.POPULAR)
        .cachedIn(viewModelScope)
}