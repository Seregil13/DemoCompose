package com.example.domain

import com.example.network.MovieApi

enum class MovieApi {
    POPULAR,
    LATEST,
    TOP_RATED,
    UPCOMING,
    NOW_PLAYING
    ;

    internal fun toNetworkApi(): MovieApi = when(this) {
        POPULAR -> MovieApi.POPULAR
        LATEST -> MovieApi.LATEST
        TOP_RATED -> MovieApi.TOP_RATED
        UPCOMING -> MovieApi.UPCOMING
        NOW_PLAYING -> MovieApi.NOW_PLAYING
    }
}