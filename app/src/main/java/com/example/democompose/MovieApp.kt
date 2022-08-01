package com.example.democompose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.democompose.exploremovie.MovieExploreScreen
import com.example.democompose.movielist.MovieListScreen
import com.example.domain.MovieApi
import timber.log.Timber

@Composable
fun MovieApp() {
    val navController = rememberNavController()

    var title by remember { mutableStateOf("The Movie DB") }
    var showUpButton by remember { mutableStateOf(false) }

    MovieAppScaffold(
        showUpButton = showUpButton,
        onUpButtonClicked = { navController.navigateUp() },
        title = title,
    ){
        NavHost(
            navController = navController,
            startDestination = "movies/explore"
        ) {
            composable(route = "movies/explore") {
                MovieExploreScreen(
                    showUpButton = { b -> showUpButton = b },
                    setScaffoldTitle = { s -> title = s },
                    navigateToMovieList = { navController.navigate("movies/list/${it}") },
                    navigateToMovieDetails = { navController.navigate("movies/details/${it}") }
                )
            }

            composable(
                route = "movies/details/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType }
                )
            ) {
                TODO()
            }

            composable(
                route = "movies/list/{sort}",
                arguments = listOf(
                    navArgument("sort") {
                        type = NavType.StringType
                        defaultValue = MovieApi.POPULAR.name
                    }
                )
            ) {
                Timber.d(it.arguments?.toString())
                val api: MovieApi = MovieApi.fromString(it.arguments?.getString("sort"))
                Timber.d(api.toDisplayName())

                MovieListScreen(
                    movieApi = api,
                    setScaffoldTitle = { t -> title = t },
                    showUpButton = { b -> showUpButton = b },
                    navigateToDetails = { id -> navController.navigate("movies/details/${id}") }
                )
            }
        }
    }
}

@Composable
fun MovieAppScaffold(
    showUpButton: Boolean,
    onUpButtonClicked: () -> Unit,
    title: String,
    appContent: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        modifier = Modifier,
                        style = TextStyle(
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                },
                navigationIcon = {
                    if (showUpButton) {
                        IconButton(
                            onClick = { onUpButtonClicked() },
                            modifier = Modifier
                                .size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    }
                },
            )
        },
        content = appContent
    )
}
