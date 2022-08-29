package com.example.core.domain

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class MovieDetailItem(
    val id: Int,
    val backdropPath: String?,
    val budget: Int,
    val genres: List<String>,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: LocalDate?,
    val revenue: Int,
    val runtime: Int?,
    val status: MovieStatus,
    val tagline: String?,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val lastUpdateTime: Instant,
)