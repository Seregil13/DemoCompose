package com.example.democompose.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.MovieApi
import com.example.domain.MovieListRepository
import com.example.domain.database.entity.MovieListItem
import kotlinx.coroutines.flow.Flow

class MovieListViewModel(
    repository: MovieListRepository,
): ViewModel() {

    val movies: Flow<PagingData<MovieListItem>> = repository.getMovieList(MovieApi.POPULAR)
        .cachedIn(viewModelScope)
}