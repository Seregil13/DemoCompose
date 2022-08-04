package com.example.democompose.exploremovie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.democompose.ui.widget.MovieList
import com.example.domain.model.MovieApi
import com.example.domain.model.MovieListItem as Movie
import org.koin.androidx.compose.getViewModel

@Composable
fun MovieExploreScreen(
    showUpButton: (Boolean) -> Unit,
    setScaffoldTitle: (String) -> Unit,
    navigateToMovieList: (MovieApi) -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    viewModel: ExploreMoviesViewModel = getViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchData()
        showUpButton(false)
        setScaffoldTitle("Explore Movies")
    }

    val popularMovies = viewModel.popularMovies.collectAsState()
    val upcomingMovies = viewModel.upcomingMovies.collectAsState()
    val topRatedMovies = viewModel.topRatedMovies.collectAsState()
    val nowPlayingMovies = viewModel.nowPlayingMovies.collectAsState()

    LazyColumn {
        if (popularMovies.value.isNotEmpty()) {
            item {
                ExploreMovieSection(
                    movieList = popularMovies.value,
                    header = "Popular",
                    onHeaderClicked = { navigateToMovieList(MovieApi.POPULAR) },
                    onMovieClicked = { navigateToMovieDetails(it) }
                )
            }
        }

        if (upcomingMovies.value.isNotEmpty()) {
            item {
                ExploreMovieSection(
                    movieList = upcomingMovies.value,
                    header = "Upcoming",
                    onHeaderClicked = { navigateToMovieList(MovieApi.UPCOMING) },
                    onMovieClicked = { navigateToMovieDetails(it) }
                )
            }
        }

        if (topRatedMovies.value.isNotEmpty()) {
            item {
                ExploreMovieSection(
                    movieList = topRatedMovies.value,
                    header = "Top Rated",
                    onHeaderClicked = { navigateToMovieList(MovieApi.TOP_RATED) },
                    onMovieClicked = { navigateToMovieDetails(it) }
                )
            }
        }

        if (nowPlayingMovies.value.isNotEmpty()) {
            item {
                ExploreMovieSection(
                    movieList = nowPlayingMovies.value,
                    header = "Now Playing",
                    onHeaderClicked = { navigateToMovieList(MovieApi.NOW_PLAYING) },
                    onMovieClicked = { navigateToMovieDetails(it) }
                )
            }
        }
    }
}

@Composable
fun ExploreMovieSection(
    movieList: List<Movie>,
    header: String,
    onHeaderClicked: () -> Unit,
    onMovieClicked: (movieId: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { onHeaderClicked() }
        ) {
            Text(
                text = header,
                fontSize = 24.sp,
                modifier = Modifier
                    .weight(1f, true)
            )

            Text(
                text = "See All",
                fontSize =  16.sp
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "See All",
                modifier = Modifier
            )
        }

        MovieList.Horizontal(movies = movieList, onMovieClicked = onMovieClicked)
    }
}