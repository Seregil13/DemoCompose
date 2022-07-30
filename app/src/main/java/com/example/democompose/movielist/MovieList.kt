package com.example.democompose.movielist

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.democompose.ui.widget.MovieWidget
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@Composable
fun MovieList(
    viewModel: MovieListViewModel = getViewModel()
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()

    Log.d("MovieList", "MovieList: ${movies.itemCount}")

    LazyColumn {
        if (movies.loadState.refresh == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }

        items(movies.itemCount) { index ->
            val movie = movies[index] ?: return@items

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                MovieWidget.FullWidthCard(
                    movie = movie,
                    onClick = { Timber.d("Tapped movie ${movie.title}") }
                )
            }
        }

        if (movies.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
