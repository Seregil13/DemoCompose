package com.example.democompose

import android.app.Application
import com.example.democompose.exploremovie.ExploreMoviesViewModel
import com.example.democompose.movielist.MovieListViewModel
import com.example.domain.database.TheMovieDB
import com.example.domain.repository.MovieRepository
import com.example.network.ITheMovieDbApiService
import com.example.network.KtorClient
import com.example.network.NetworkLogger
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

        KtorClient.logger = object : NetworkLogger {
            override fun log(message: String) {
                Timber.d(message)
            }
        }

        startKoin {
            androidLogger()
            androidContext(this@DemoApplication)
            modules(appModule, viewModelModule)
        }

    }


    private val appModule = module {
        single { MovieRepository(get(), get()) }
        single { TheMovieDB.create(get()) }
        single<ITheMovieDbApiService> { TheMovieDbApiService }
    }

    private val viewModelModule = module {
        viewModel { params -> MovieListViewModel(params.get(), get()) }
        viewModel { ExploreMoviesViewModel(get()) }
    }
}