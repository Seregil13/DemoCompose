package com.example.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entity.MovieList

@Dao
interface MovieListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieList>)

    @Query("SELECT * FROM movie_list WHERE api = :api")
    fun pagingSource(api: String): PagingSource<Int, MovieList>

    @Query("DELETE FROM movie_list WHERE api = :api")
    suspend fun clearAll(api: String)

    @Query("DELETE FROM movie_list WHERE page = :page AND api = :api")
    suspend fun deleteByPage(api: String, page: Int)

    @Query("SELECT * FROM movie_list WHERE api = :api")
    suspend fun getMovies(api: String): List<MovieList>

    @Query("SELECT * FROM movie_list WHERE api = :api AND page = :page")
    suspend fun getMovies(api: String, page: Int): List<MovieList>

}