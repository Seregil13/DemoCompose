package com.example.democompose.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.democompose.ui.theme.DemoComposeTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import com.example.domain.model.MovieListItem as Movie

object MovieList {
    @Composable
    fun Horizontal(
        movies: List<Movie>,
        onMovieClicked: (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        LazyRow(
            modifier = modifier
        ) {
            items(movies) { movie ->
                MovieWidget.GridItem(
                    movie = movie,
                    onClick = onMovieClicked,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMovieListHorizontal() {
    DemoComposeTheme {
        MovieList.Horizontal(
            movies = listOf(
                Movie(
                    movieId = 1,
                    title = "Red Notice",
                    releaseDate = LocalDate(2020, 4, 3),
                    popularity = 4.0,
                    posterPath = null,
                    overview = "This is the overview of a movie. This is the overview of a movie. This is the overview of a movie. This is the overview of a movie. This is the overview of a movie.",
                    api = "popular",
                    page = 1,
                    lastUpdateTime = Clock.System.now()
                ),
                Movie(
                    movieId = 1,
                    title = "Jumangi",
                    releaseDate = LocalDate(2020, 4, 3),
                    popularity = 4.0,
                    posterPath = null,
                    overview = "This is the overview of a movie. This is the overview of a movie. This is the overview of a movie. This is the overview of a movie. This is the overview of a movie.",
                    api = "popular",
                    page = 1,
                    lastUpdateTime = Clock.System.now()
                )
            ), onMovieClicked = {}
        )
    }
}
