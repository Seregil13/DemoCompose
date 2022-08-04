package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.converters.InstantConverter
import com.example.database.converters.LocalDateConverter
import com.example.database.dao.MovieDetailDao
import com.example.database.dao.MovieListDao
import com.example.database.dao.PagingKeysDao
import com.example.database.entity.MovieDetail
import com.example.database.entity.MovieList
import com.example.database.entity.PagingKeys


@Database(
    entities = [
        MovieList::class,
        PagingKeys::class,
        MovieDetail::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class, InstantConverter::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieListDao(): MovieListDao
    abstract fun movieDetailsDao(): MovieDetailDao
    abstract fun pagingKeysDao(): PagingKeysDao

    companion object {
        fun create(context: Context): MoviesDatabase = Room
            .databaseBuilder(context, MoviesDatabase::class.java, "themoviedb.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}