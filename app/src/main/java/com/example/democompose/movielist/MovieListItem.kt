package com.example.democompose.movielist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.democompose.R
import com.example.democompose.ui.theme.DemoComposeTheme
import com.example.domain.ImageUrlBuilder
import com.example.domain.PosterImageSize
import kotlinx.datetime.LocalDate
import com.example.domain.database.entity.MovieListItem as Movie

@Composable
fun MovieListItem(
    movie: Movie,
    onClick: (id: Int) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(movie.id)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            if (movie.posterUrl != null) {
                AsyncImage(
                    model = ImageUrlBuilder.getMoviePosterImageUrl(size = PosterImageSize.W185,movie.posterUrl!!),
                    contentDescription = movie.title,
                    placeholder = painterResource(id = R.drawable.large_movie_poster),
                    modifier = Modifier
                )
            }

            Column(
                modifier = Modifier
            ) {
                Text(
                    text = movie.title,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    softWrap = false,
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}

@Preview
@Composable
fun PreviewMovieListItem() {
    DemoComposeTheme {
        MovieListItem(
            movie = Movie(
                id = 0,
                movieId = 1,
                title = "Red Notice",
                releaseDate = LocalDate(2020, 4, 3),
                popularity = 4.0,
                posterUrl = null,
                overview = "This is the overview of a movie.",
                api = "popular",
                page = 1,
                lastUpdateTime = null
            ),
            onClick = {}
        )
    }
}
