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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.ImageUrlBuilder
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
                    model = ImageUrlBuilder.getMoviePosterImageUrl(movie.posterUrl!!),
                    contentDescription = movie.title,
                    modifier = Modifier.weight(1f)
                )
            }

            Column(
                modifier = Modifier.weight(4f, true)
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
