package com.example.democompose.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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

object MovieWidget {

    @Composable
    fun FullWidthCard(
        movie: Movie,
        onClick: (movieId: Int) -> Unit
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

                MovieImage(
                    imagePath = movie.posterUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxHeight()
                )

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = movie.title,
                        fontSize = 24.sp,
                        softWrap = false,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Divider()

                    movie.releaseDate?.let {
                        Text(
                            text = "Released ${it.month} ${it.dayOfMonth}, ${it.year}",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    Text(
                        text = movie.overview,
                        maxLines = 3,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun GridItem(
        movie: Movie,
        onClick: (movieId: Int) -> Unit
    ) {
        Card(
            modifier = Modifier
                .clickable { onClick(movie.movieId) }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MovieImage(imagePath = movie.posterUrl, contentDescription = movie.title)
                Text(text = movie.title)
            }
        }
    }

    @Composable
    fun MovieImage(
        imagePath: String?,
        contentDescription: String,
        modifier: Modifier = Modifier
    ) {
        if (imagePath != null) {
            AsyncImage(
                model = ImageUrlBuilder.getMoviePosterImageUrl(
                    path = imagePath,
                    size = PosterImageSize.W342
                ),
                placeholder = painterResource(id = R.drawable.large_movie_poster),
                contentDescription = contentDescription,
                modifier = modifier
                    .fillMaxHeight()
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.movies_placeholder),
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = modifier
                    .fillMaxHeight()
            )
        }
    }
}

@Preview
@Composable
fun MovieFullWidthCardPreview() {
    DemoComposeTheme {
        MovieWidget.FullWidthCard(
            movie = Movie(
                id = 0,
                movieId = 1,
                title = "Red Notice",
                releaseDate = LocalDate(2020, 4, 3),
                popularity = 4.0,
                posterUrl = null,
                overview = "This is the overview of a movie. This is the overview of a movie. This is the overview of a movie. This is the overview of a movie. This is the overview of a movie.",
                api = "popular",
                page = 1,
                lastUpdateTime = null
            ),
            onClick = {}
        )
    }
}