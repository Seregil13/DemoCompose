package com.example.network

enum class MovieApi(val path: String) {
    POPULAR("movie/popular"),
    LATEST("movie/latest"),
    TOP_RATED("movie/top_rated"),
    UPCOMING("movie/upcoming"),
    NOW_PLAYING("movie/now_playing")
}