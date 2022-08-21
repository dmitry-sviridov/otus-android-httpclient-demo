package ru.otus.httpclientsdemo.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.otus.httpclientsdemo.repository.PostRepository
import ru.otus.httpclientsdemo.repository.PostRepositoryImpl
import ru.otus.httpclientsdemo.repository.PostsApi
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
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl("http://jsonplaceholder.typicode.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))


    @Singleton
    @Provides
    fun providePostsApi(retrofit: Retrofit.Builder): PostsApi =
        retrofit
            .build()
            .create(PostsApi::class.java)


    @Singleton
    @Provides
    fun providePostRepository(postsApi: PostsApi): PostRepository = PostRepositoryImpl(postsApi)

}