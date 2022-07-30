package com.example.democompose.exploremovie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.androidx.compose.getViewModel

@Composable
fun ExploreScreen(
    viewModel: ExploreMoviesViewModel = getViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchData()
    }

    // Row popular
    // Row top rated
    // row upcoming


}