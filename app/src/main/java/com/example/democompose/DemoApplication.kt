package com.example.democompose

import android.app.Application
import com.example.democompose.exploremovie.ExploreMoviesViewModel
import com.example.democompose.movielist.MovieListViewModel
import com.example.domain.databaseModule
import com.example.domain.domainModule
import com.example.network.KtorClient
import com.example.network.NetworkLogger
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
            modules(appModule, viewModelModule, domainModule, databaseModule)
        }

    }


    private val appModule = module {
    }

    private val viewModelModule = module {
        viewModel { params -> MovieListViewModel(params.get(), get()) }
        viewModel { ExploreMoviesViewModel(get()) }
    }
}