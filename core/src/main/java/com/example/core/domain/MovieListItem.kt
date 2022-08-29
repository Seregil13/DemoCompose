package com.example.core.domain

import kotlinx.datetime.LocalDate

data class MovieListItem(
    val movieId: Int,
    val title: String,
    val releaseDate: LocalDate?,
    val popularity: Double,
    val posterPath: String?,
    val overview: String
)
