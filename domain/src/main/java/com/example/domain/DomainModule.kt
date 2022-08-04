package com.example.domain

import com.example.database.MoviesDatabase
import com.example.domain.repository.MovieRepository
import com.example.network.ITheMovieDbApiService
import com.example.network.TheMovieDbApiService
import org.koin.dsl.module

val domainModule = module {
    single { MovieRepository(get(), get()) }
    single<ITheMovieDbApiService> { TheMovieDbApiService }
}

val databaseModule = module {
    single { MoviesDatabase.create(get()) }
}