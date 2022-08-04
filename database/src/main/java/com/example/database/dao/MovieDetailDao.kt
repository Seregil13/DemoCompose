package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.database.entity.MovieDetail

@Dao
interface MovieDetailDao {
    @Insert
    suspend fun insert(movie: MovieDetail)

    @Query("SELECT * FROM movie_details WHERE id = :id")
    suspend fun getDetails(id: Int): MovieDetail?

    @Query("DELETE FROM movie_details WHERE id = :id")
    suspend fun delete(id: Int)
}