package com.incrowdsports.task.di

import com.incrowdsports.task.BuildConfig
import com.incrowdsports.task.data.FixtureService
import com.incrowdsports.task.data.Repository
import com.incrowdsports.task.data.remote.RemoteDataSource
import com.incrowdsports.task.ui.fixture.FixtureListViewModel
import com.incrowdsports.task.ui.matchdetails.MatchDetailsViewModel
import com.incrowdsports.task.utils.CustomHttpLogging
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {

}

val viewModelModule = module {
    viewModel { FixtureListViewModel(repository = get()) }
    viewModel { MatchDetailsViewModel(repository = get()) }
}

val remoteModule = module {

    single { createOkHttpClient() }
    single {
        createWebService<FixtureService>(
            okHttpClient = get(),
            url = BuildConfig.BASE_URL,
            gsonConverterFactory = provideConverterFactory()
        )
    }

}

val repositoryModule = module {
    single { RemoteDataSource(fixtureService = get()) }

    single { Repository(remoteDataSource = get()) }
}

fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()

fun createOkHttpClient(): OkHttpClient {
    val loggingInterceptor =
        HttpLoggingInterceptor(CustomHttpLogging()).setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
    url: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
    return retrofit.create(T::class.java)
}

// Gather all app modules
val appModules = listOf(mainModule, viewModelModule, remoteModule, repositoryModule)