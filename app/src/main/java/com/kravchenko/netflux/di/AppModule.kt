package com.kravchenko.netflux.di

import android.content.Context
import coil.ImageLoader
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kravchenko.netflux.BuildConfig
import com.kravchenko.netflux.network.Api
import com.kravchenko.netflux.repository.MovieRepository
import com.kravchenko.netflux.utils.GsonFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonFactory.gson
    }

    @Provides
    @Singleton
    fun provideTMDBService(gson: Gson): Api {
        val interceptor = Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                .build()
            chain.proceed(newRequest)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(service: Api): MovieRepository {
        return MovieRepository(service)
    }

    @Provides
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}