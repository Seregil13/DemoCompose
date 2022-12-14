package com.example.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

@Entity(tableName = "movie_list")
data class MovieList(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: LocalDate?,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_url") val posterPath: String?,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "api") val api: String,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "last_updated") val lastUpdateTime: Instant
)

