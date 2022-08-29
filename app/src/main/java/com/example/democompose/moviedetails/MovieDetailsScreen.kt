package com.example.democompose.moviedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.democompose.moviedetails.MovieDetailsViewModel.State
import com.example.domain.model.MovieDetailItem as Movie

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    setScaffoldTitle: (String) -> Unit,
    showUpButton: (Boolean) -> Unit,
    viewModel: MovieDetailsViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.load(movieId)
        showUpButton(true)
    }

    viewModel.movie.collectAsState().value.let {  state ->
        when (state) {
            is State.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Something went wrong",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            is State.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            is State.Success -> {
                setScaffoldTitle(state.movie.title)

                MovieDetails(state.movie)

            }
        }
    }
}

@Composable
fun MovieDetails(
    movie: Movie
) {
    
    Text(text = movie.title)

}

