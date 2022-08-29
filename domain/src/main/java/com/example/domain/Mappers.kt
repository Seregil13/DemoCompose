package com.example.domain

import com.example.domain.model.MovieApi
import com.example.domain.model.MovieStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDate
import com.example.database.entity.MovieDetail as DatabaseMovieDetail
import com.example.database.entity.MovieList as DatabaseMovie
import com.example.domain.model.MovieDetailItem as DomainMovieDetail
import com.example.domain.model.MovieListItem as DomainMovie
import com.example.network.response.MovieListItemResponse as NetworkMovie
import com.example.network.response.MovieResponse as NetworkMovieDetail

fun DatabaseMovie.toDomainMovie(): DomainMovie {
    return DomainMovie(
        movieId = this.movieId,
        title = this.title,
        releaseDate = this.releaseDate,
        popularity = this.popularity,
        posterPath = this.posterPath,
        overview = this.overview,
        api = this.api,
        page = this.page,
        lastUpdateTime = this.lastUpdateTime,
    )
}

fun NetworkMovie.toDomainMovie(
    api: MovieApi,
    page: Int,
): DomainMovie {
    return DomainMovie(
        movieId = this.id,
        title = this.title,
        releaseDate = this.releaseDate.toLocalDate(),
        popularity = this.popularity,
        posterPath = this.posterPath,
        overview = this.overview,
        api = api.name,
        page = page,
        lastUpdateTime = Clock.System.now(),
    )
}

fun DomainMovie.toDatabaseMovie(): DatabaseMovie {
    return DatabaseMovie(
        movieId = this.movieId,
        title = this.title,
        releaseDate = this.releaseDate,
        popularity = this.popularity,
        posterPath = this.posterPath,
        overview = this.overview,
        api = this.api,
        page = this.page,
        lastUpdateTime = this.lastUpdateTime
    )
}

fun DatabaseMovieDetail.toDomainMovieDetail(): DomainMovieDetail {
    return DomainMovieDetail(
        id = this.id,
        backdropPath = this.backdropPath,
        budget = this.budget,
        genres = this.genres.split(","),
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        status = MovieStatus.valueOf(this.status),
        tagline = this.tagline,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        lastUpdateTime = this.lastUpdateTime,
    )
}

fun DomainMovieDetail.toDatabaseMovieDetail(): DatabaseMovieDetail {
    return DatabaseMovieDetail(
        id = this.id,
        backdropPath = this.backdropPath,
        budget = this.budget,
        genres = this.genres.joinToString(separator = ",") { it },
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        status = this.status.name,
        tagline = this.tagline,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        lastUpdateTime = this.lastUpdateTime,
    )
}

fun NetworkMovieDetail.toDomainMovieDetail(): DomainMovieDetail {
    return DomainMovieDetail(
        id = this.id,
        backdropPath = this.backdropPath,
        budget = this.budget,
        genres = this.genres.map { it.name },
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate.toLocalDate(),
        revenue = this.revenue,
        runtime = this.runtime,
        status = this.status?.let { MovieStatus.valueOf(it) } ?: MovieStatus.RUMORED,
        tagline = this.tagline,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        lastUpdateTime = Clock.System.now(),
    )
}