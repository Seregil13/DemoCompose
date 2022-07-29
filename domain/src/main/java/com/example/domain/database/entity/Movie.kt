package com.example.domain.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

@kotlinx.serialization.Serializable
@Entity(tableName = "movies")
data class MovieListItem(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: LocalDate?,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_url") val posterUrl: String?,
    @ColumnInfo(name = "api") val api: String,
    @ColumnInfo(name = "last_updated") val lastUpdateTime: Instant?
)
