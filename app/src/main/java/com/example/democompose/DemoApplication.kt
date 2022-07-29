package com.example.democompose

import android.app.Application
import com.example.democompose.movielist.MovieListViewModel
import com.example.domain.MovieListRepository
import com.example.domain.database.TheMovieDB
import com.example.network.ITheMovieDbApiService
import com.example.network.TheMovieDbApiService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@DemoApplication)
            modules(appModule)
        }

    }


    private val appModule = module {
        single { MovieListRepository(get(), get()) }
        viewModel { MovieListViewModel(get()) }
        single<TheMovieDB> { TheMovieDB.create(get()) }
        single<ITheMovieDbApiService> { TheMovieDbApiService }
    }
}