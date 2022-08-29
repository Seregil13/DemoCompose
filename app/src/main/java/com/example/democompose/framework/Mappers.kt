package com.example.democompose.framework

import com.example.core.domain.MovieApi
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDate
import com.example.core.domain.MovieListItem as CoreMovie
import com.example.database.entity.MovieList as DatabaseMovie
import com.example.network.MovieApi as NetworkApi
import com.example.network.response.MovieListItemResponse as NetworkMovie

fun DatabaseMovie.toCoreMovie(): CoreMovie {
    return CoreMovie(
        movieId = this.movieId,
        title = this.title,
        releaseDate = this.releaseDate,
        popularity = this.popularity,
        posterPath = this.posterPath,
        overview = this.overview
    )
}

fun CoreMovie.toDatabaseMovie(api: MovieApi, page: Int): DatabaseMovie {
    return DatabaseMovie(
        movieId = this.movieId,
        title = this.title,
        releaseDate = this.releaseDate,
        popularity = this.popularity,
        posterPath = this.posterPath,
        overview = this.overview,
        api = api.name,
        page = page,
        lastUpdateTime = Clock.System.now()
    )
}

fun NetworkMovie.toCoreMovie(): CoreMovie {
    return CoreMovie(
        movieId = this.id,
        title = this.title,
        releaseDate = this.releaseDate.toLocalDate(),
        popularity = this.popularity,
        posterPath = this.posterPath,
        overview = this.overview
    )
}

fun MovieApi.toNetworkApi(): NetworkApi = when (this) {
    MovieApi.POPULAR -> NetworkApi.POPULAR
    MovieApi.LATEST -> NetworkApi.LATEST
    MovieApi.TOP_RATED -> NetworkApi.TOP_RATED
    MovieApi.UPCOMING -> NetworkApi.UPCOMING
    MovieApi.NOW_PLAYING -> NetworkApi.NOW_PLAYING
}