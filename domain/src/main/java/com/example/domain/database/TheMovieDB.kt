package com.example.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.domain.database.entity.MovieListItem
import com.example.domain.database.entity.PageKeys
import timber.log.Timber

@Database(entities = [MovieListItem::class, PageKeys::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class TheMovieDB: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun pageDao(): PageKeysDao

    companion object {
        fun create(context: Context): TheMovieDB {
            Timber.d("Creating room database")
            return Room.databaseBuilder(context, TheMovieDB::class.java, "themoviedb.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}