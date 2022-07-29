package com.example.domain.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.database.entity.MovieListItem

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieListItem>)

    @Query("SELECT * FROM movies WHERE api = :api")
    fun pagingSource(api: String): PagingSource<Int, MovieListItem>

    @Query("DELETE FROM movies WHERE api = :api")
    suspend fun clearAll(api: String)

    @Query("SELECT * FROM movies WHERE api = :api")
    suspend fun getMovies(api: String): List<MovieListItem>

}
