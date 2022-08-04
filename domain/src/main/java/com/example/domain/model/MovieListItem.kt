package com.example.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class MovieListItem(
    val movieId: Int,
    val title: String,
    val releaseDate: LocalDate?,
    val popularity: Double,
    val posterPath: String?,
    val overview: String,
    val api: String,
    val page: Int,
    val lastUpdateTime: Instant
)
