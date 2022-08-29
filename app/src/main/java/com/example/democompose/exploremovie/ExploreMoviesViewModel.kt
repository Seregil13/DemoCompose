package com.example.democompose.exploremovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Outcome
import com.example.core.domain.MovieApi
import com.example.core.interactors.GetMovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.core.domain.MovieListItem as Movie

class ExploreMoviesViewModel(
    private val getMovieList: GetMovieList
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()


    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            launch { getPopularMovies() }
            launch { getTopRatedMovies() }
            launch { getUpcomingMovies() }
            launch { getNowPlayingMovies() }
        }
    }

    private suspend fun getPopularMovies() {
        when (val movies = getMovieList(MovieApi.POPULAR, 1)) {
            is Outcome.Error -> _state.value = state.value.copy(
                popularMovies = state.value.popularMovies.copy(
                    loading = false,
                    error = movies.reason
                )
            )
            is Outcome.Success ->  _state.value = state.value.copy(
                popularMovies = state.value.popularMovies.copy(
                    movies = movies.value,
                    loading = false,
                )
            )
        }
    }

    private suspend fun getNowPlayingMovies() {
        when (val movies = getMovieList(MovieApi.NOW_PLAYING, 1)) {
            is Outcome.Error -> _state.value = state.value.copy(
                nowPlayingMovies = state.value.nowPlayingMovies.copy(
                    loading = false,
                    error = movies.reason
                )
            )
            is Outcome.Success ->  _state.value = state.value.copy(
                nowPlayingMovies = state.value.nowPlayingMovies.copy(
                    movies = movies.value,
                    loading = false,
                )
            )
        }
    }

    private suspend fun getTopRatedMovies() {
        when (val movies = getMovieList(MovieApi.TOP_RATED, 1)) {
            is Outcome.Error -> _state.value = state.value.copy(
                topRatedMovies = state.value.topRatedMovies.copy(
                    loading = false,
                    error = movies.reason
                )
            )
            is Outcome.Success ->  _state.value = state.value.copy(
                topRatedMovies = state.value.topRatedMovies.copy(
                    movies = movies.value,
                    loading = false,
                )
            )
        }
    }

    private suspend fun getUpcomingMovies() {
        when (val movies = getMovieList(MovieApi.UPCOMING, 1)) {
            is Outcome.Error -> _state.value = state.value.copy(
                upcomingMovies = state.value.upcomingMovies.copy(
                    loading = false,
                    error = movies.reason
                )
            )
            is Outcome.Success ->  _state.value = state.value.copy(
                upcomingMovies = state.value.upcomingMovies.copy(
                    movies = movies.value,
                    loading = false,
                )
            )
        }
    }

    data class State(
        val popularMovies: ExploreSectionState = ExploreSectionState(),
        val topRatedMovies: ExploreSectionState = ExploreSectionState(),
        val nowPlayingMovies: ExploreSectionState = ExploreSectionState(),
        val upcomingMovies: ExploreSectionState = ExploreSectionState()
    )

    data class ExploreSectionState(
        val movies: List<Movie> = emptyList(),
        val loading: Boolean = true,
        val error: String? = null
    )
}