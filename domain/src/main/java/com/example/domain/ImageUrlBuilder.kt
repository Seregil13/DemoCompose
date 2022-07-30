package com.example.domain

object ImageUrlBuilder {
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

    fun getMoviePosterImageUrl(
        size: PosterImageSize,
        path: String
    ): String {
        return "$IMAGE_BASE_URL${size.pathArg}$path"
    }

}
