package com.example.democompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.democompose.movielist.MovieList
import com.example.democompose.ui.theme.DemoComposeTheme
import com.example.network.KtorClient
import com.example.network.NetworkLogger

class MainActivity : ComponentActivity() {


//    private val configurationRepository = ConfigurationRepository(TheMovieDbApiService)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KtorClient.logger = object : NetworkLogger {
            override fun log(message: String) {
                Log.d("NetworkLogger", message)
            }
        }

        setContent {
            DemoComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MovieList()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DemoComposeTheme {
        Greeting("Android")
    }
}