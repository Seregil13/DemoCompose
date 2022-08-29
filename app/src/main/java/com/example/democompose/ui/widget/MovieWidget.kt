package com.example.democompose.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.democompose.R
import com.example.democompose.ui.theme.DemoComposeTheme
import com.example.domain.ImageUrlBuilder
import com.example.domain.PosterImageSize
import kotlinx.datetime.LocalDate
import com.example.core.domain.MovieListItem as Movie

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
                    onClick(movie.movieId)
                }
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ){
                val (image, title, divider, releaseDate, summary) = createRefs()

                MovieImage(
                    imagePath = movie.posterPath,
                    imageSize = PosterImageSize.W500,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .constrainAs(image) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                bias = 0f
                            )
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.fillToConstraints
                        }
                )

                Text(
                    text = movie.title,
                    fontSize = 24.sp,
                    softWrap = false,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(parent.top, margin = 8.dp)
                            bottom.linkTo(divider.top, margin = 4.dp)
                            start.linkTo(image.end, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        }
                )

                Divider(
                    modifier = Modifier
                        .constrainAs(divider) {
                            start.linkTo(image.end)
                            end.linkTo(parent.end)
                            top.linkTo(title.bottom)
                            bottom.linkTo(releaseDate.top)
                        }
                )

                movie.releaseDate?.let {
                    Text(
                        text = "Released ${it.month} ${it.dayOfMonth}, ${it.year}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .constrainAs(releaseDate) {
                                start.linkTo(image.end, margin = 8.dp)
                                end.linkTo(parent.end, margin = 8.dp)
                                top.linkTo(divider.bottom, margin = 4.dp)
                                bottom.linkTo(summary.top)
                            }
                    )
                }

                Text(
                    text = movie.overview,
                    maxLines = 3,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .constrainAs(summary) {
                            linkTo(
                                top = releaseDate.bottom,
                                bottom = parent.bottom,
                                topMargin = 8.dp,
                                bottomMargin = 8.dp,
                                bias = 1f
                            )

                            start.linkTo(image.end, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            
                            width = Dimension.fillToConstraints
                        }
                )
            }
        }
    }

    @Composable
    fun GridItem(
        movie: Movie,
        onClick: (movieId: Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        var titleText by remember { mutableStateOf(movie.title) }

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .clickable { onClick(movie.movieId) }
        ) {
            ConstraintLayout {
                val (image, title) = createRefs()

                MovieImage(
                    imagePath = movie.posterPath,
                    imageSize = PosterImageSize.W500,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                )
                Text(
                    text = titleText,
                    overflow = TextOverflow.Clip,
                    softWrap = true,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    onTextLayout = { textLayoutResult ->
                        // the following causes a recomposition if there isn't enough text to occupy the minimum number of lines!
                        if ((textLayoutResult.lineCount) < 2) {
                            // don't forget the space after the line break, otherwise empty lines won't get full height!
                            titleText = movie.title + "\n ".repeat(2 - textLayoutResult.lineCount)
                        }
                    },
                    modifier = Modifier
                        .constrainAs(title) {
                            start.linkTo(image.start)
                            end.linkTo(image.end)
                            top.linkTo(image.bottom)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                )
            }
        }
    }

    @Composable
    fun MovieImage(
        imagePath: String?,
        imageSize: PosterImageSize,
        contentDescription: String,
        modifier: Modifier = Modifier
    ) {
        if (imagePath != null) {
            AsyncImage(
                model = ImageUrlBuilder.getMoviePosterImageUrl(
                    path = imagePath,
                    size = imageSize
                ),
                placeholder = painterResource(id = R.drawable.movies_placeholder),
                contentDescription = contentDescription,
                modifier = modifier
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.movies_placeholder),
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = modifier
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
                movieId = 1,
                title = "Red Notice",
                releaseDate = LocalDate(2020, 4, 3),
                popularity = 4.0,
                posterPath = null,
                overview = "This is the overview of a movie. This is the overview of a movie. This is the overview of a movie. This is the overview of a movie. This is the overview of a movie.",
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewGridItem() {
    DemoComposeTheme {
        MovieWidget.GridItem(
            movie = Movie(
                movieId = 1,
                title = "Red Notice",
                releaseDate = LocalDate(2020, 4, 3),
                popularity = 4.0,
                posterPath = null,
                overview = "This is the overview of a movie. This is the overview of a movie. This is the overview of a movie. This is the overview of a movie. This is the overview of a movie.",
            ),
            onClick = {}
        )
    }
}