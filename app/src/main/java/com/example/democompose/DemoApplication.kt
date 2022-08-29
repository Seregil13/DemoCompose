package com.example.democompose

import android.app.Application
import com.example.core.data.MovieListLocalDataSource
import com.example.core.data.MovieListRemoteDataSource
import com.example.core.data.MovieListRepository
import com.example.core.interactors.GetMovieDetails
import com.example.core.interactors.GetMovieList
import com.example.database.MoviesDatabase
import com.example.democompose.exploremovie.ExploreMoviesViewModel
import com.example.democompose.framework.MovieListDatabaseDataSource
import com.example.democompose.framework.MovieListNetworkDataSource
import com.example.democompose.movielist.MovieListViewModel
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
            modules(appModule)
        }

    }

    private val appModule = module {
        single<MovieListLocalDataSource> { MovieListDatabaseDataSource(get()) }
        single<MovieListRemoteDataSource> { MovieListNetworkDataSource(get()) }

        single { MovieListRepository(get(), get()) }

        single { GetMovieList(get()) }
        single { GetMovieDetails(get()) }

        single<ITheMovieDbApiService> { TheMovieDbApiService }
        single { MoviesDatabase.create(get()) }

        viewModel { params -> MovieListViewModel(params.get(), get()) }
        viewModel { ExploreMoviesViewModel(get()) }
    }
}
