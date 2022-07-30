package com.example.domain.database.entity

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.network.response.MovieListItemResponse
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

@kotlinx.serialization.Serializable
@Entity(tableName = "movies")
@Stable
data class MovieListItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: LocalDate?,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_url") val posterUrl: String?,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "api") val api: String,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "last_updated") val lastUpdateTime: Instant?
) {

    companion object {
        fun fromNetworkObject(
            m: MovieListItemResponse,
            apiName: String,
            page: Int
        ) = MovieListItem(
            movieId = m.id,
            title = m.title,
            releaseDate = if (m.releaseDate.isNotBlank()) m.releaseDate.toLocalDate() else null,
            popularity = m.popularity,
            posterUrl = m.posterPath,
            overview = m.overview,
            api = apiName,
            page = page,
            lastUpdateTime = Clock.System.now()
        )
    }
}
