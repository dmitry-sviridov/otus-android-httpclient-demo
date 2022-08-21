package ru.otus.httpclientsdemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import ru.otus.httpclientsdemo.repository.PostRepository
import ru.otus.httpclientsdemo.repository.PostRepositoryImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("BASE_URL")
    fun provideBaseUrl() = "http://jsonplaceholder.typicode.com"

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient = HttpClient(Android) {
        engine {
            connectTimeout = 500
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.BODY
        }

        install(ContentNegotiation) {
            json()
        }
    }

    @Singleton
    @Provides
    fun providePostRepository(
        @Named("BASE_URL") baseUrl: String,
        httpClient: HttpClient
    ): PostRepository = PostRepositoryImpl(baseUrl, httpClient)

}