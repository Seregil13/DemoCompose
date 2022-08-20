package com.example.democompose.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.domain.model.MovieDetailItem as Movie

class MovieDetailsViewModel(
    private val repository: MovieRepository
): ViewModel() {

    private val _movie: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val movie = _movie.asStateFlow()

    fun load(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMovieDetails(movieId)
        }
    }


    sealed interface State {
        object Loading: State
        data class Success(val movie: Movie): State
        data class Error(val e: Exception): State
    }

}