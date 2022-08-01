package com.example.domain

import com.example.network.MovieApi as NetworkApi

enum class MovieApi {
    POPULAR,
    LATEST,
    TOP_RATED,
    UPCOMING,
    NOW_PLAYING
    ;

    internal fun toNetworkApi(): NetworkApi = when (this) {
        POPULAR -> NetworkApi.POPULAR
        LATEST -> NetworkApi.LATEST
        TOP_RATED -> NetworkApi.TOP_RATED
        UPCOMING -> NetworkApi.UPCOMING
        NOW_PLAYING -> NetworkApi.NOW_PLAYING
    }

    fun toDisplayName(): String = name
        .lowercase()
        .split("_")
        .map {
            it.replaceFirstChar { c -> c.titlecase() }
        }
        .joinToString(separator = " ") { it }

    companion object {
        fun fromString(string: String?): MovieApi {
            return when (string) {
                "POPULAR" -> POPULAR
                "TOP_RATED" -> TOP_RATED
                "UPCOMING" -> UPCOMING
                "NOW_PLAYING" -> NOW_PLAYING
                else -> POPULAR
            }
        }
    }
}