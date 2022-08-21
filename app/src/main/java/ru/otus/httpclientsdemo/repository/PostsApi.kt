package ru.otus.httpclientsdemo.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.otus.httpclientsdemo.model.Post

interface PostsApi {

    @GET("/posts/{id}")
    suspend fun fetchPosts(@Path(value = "id") postId: String): Response<Post>
}