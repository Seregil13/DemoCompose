package com.example.democompose.movielist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.core.domain.MovieApi
import com.example.democompose.ui.widget.MovieWidget
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieListScreen(
    movieApi: MovieApi,
    navigateToDetails: (Int) -> Unit,
    setScaffoldTitle: (String) -> Unit,
    showUpButton: (Boolean) -> Unit,
    viewModel: MovieListViewModel = getViewModel(parameters = { parametersOf(movieApi) })
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadNextItems()
        setScaffoldTitle(movieApi.toDisplayName())
        showUpButton(true)
    }

    LazyColumn {
        if (state.movies.isEmpty() && state.loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }

        items(state.movies.size) {index ->
            val movie = state.movies[index]
            if (index >= state.movies.size - 1 && !state.endReached && !state.loading) {
                viewModel.loadNextItems()
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                MovieWidget.FullWidthCard(
                    movie = movie,
                    onClick = { navigateToDetails(it) }
                )
            }
        }

        if (state.movies.isNotEmpty() && state.loading) {
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
