package com.example.democompose.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DefaultPaginator
import com.example.core.domain.MovieApi
import com.example.core.domain.MovieListItem
import com.example.core.interactors.GetMovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieApi: MovieApi,
    private val getMovieList: GetMovieList,
): ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val paginator = DefaultPaginator(
        initialKey = state.value.page,
        onLoadUpdated = {
            _state.value = state.value.copy(loading = it)
        },
        onRequest = { nextPage ->
            getMovieList(movieApi, nextPage)
        },
        getNextKey = {
            state.value.page + 1
        },
        onError = {
            _state.value = state.value.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            _state.value = state.value.copy(
                movies = state.value.movies + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    fun loadNextItems() {
        viewModelScope.launch(Dispatchers.IO) {
            paginator.loadNextItems()
        }
    }

    data class State(
        val loading: Boolean = true,
        val error: String? = null,
        val endReached: Boolean = false,
        val movies: List<MovieListItem> = emptyList(),
        val page: Int = 1
    )
}