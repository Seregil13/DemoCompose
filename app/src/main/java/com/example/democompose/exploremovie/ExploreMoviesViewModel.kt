package com.example.democompose.exploremovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.MovieApi
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.domain.database.entity.MovieListItem as Movie

class ExploreMoviesViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _popularMovies: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    private val _topRatedMovies: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val topRatedMovies: StateFlow<List<Movie>> = _topRatedMovies

    private val _upcomingMovies: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val upcomingMovies: StateFlow<List<Movie>> = _upcomingMovies

    private val _nowPlayingMovies: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val nowPlayingMovies: StateFlow<List<Movie>> = _nowPlayingMovies

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            launch { getPopularMovies() }
            launch { getTopRatedMovies() }
            launch { getUpcomingMovies() }
            launch { getNowPlayingMovies() }
        }
    }

    private suspend fun getPopularMovies() {
        val movies = repository.getMovieList(MovieApi.POPULAR, 1)
        _popularMovies.value = movies
    }

    private suspend fun getNowPlayingMovies() {
        val movies = repository.getMovieList(MovieApi.NOW_PLAYING, 1)
        _nowPlayingMovies.value = movies
    }

    private suspend fun getTopRatedMovies() {
        val movies = repository.getMovieList(MovieApi.TOP_RATED, 1)
        _topRatedMovies.value = movies
    }

    private suspend fun getUpcomingMovies() {
        val movies = repository.getMovieList(MovieApi.UPCOMING, 1)
        _upcomingMovies.value = movies
    }
}