package ru.otus.httpclientsdemo.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.otus.httpclientsdemo.repository.PostRepository
import ru.otus.httpclientsdemo.repository.PostRepositoryImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl(): String = "http://jsonplaceholder.typicode.com"

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .apply { setLevel(HttpLoggingInterceptor.Level.BODY) }


    @Singleton
    @Provides
    fun provideOkHttp(loggerInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggerInterceptor)
            .build()


    @Singleton
    @Provides
    fun providePostRepository(
        @Named("BASE_URL") baseUrl: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): PostRepository = PostRepositoryImpl(baseUrl, gson, okHttpClient)

}