package com.example.democompose.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.MovieApi
import com.example.domain.model.MovieListItem
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieListViewModel(
    movieApi: MovieApi,
    repository: MovieRepository,
): ViewModel() {

    val movies: Flow<PagingData<MovieListItem>> = repository.getPagedMovieList(movieApi)
        .cachedIn(viewModelScope)
}