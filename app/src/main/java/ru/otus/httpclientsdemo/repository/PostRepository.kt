package ru.otus.httpclientsdemo.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.otus.httpclientsdemo.domain.ResourceState
import ru.otus.httpclientsdemo.model.Post
import javax.inject.Inject

interface PostRepository {
    suspend fun fetchPostById(postId: String): ResourceState<Post>
}

class PostRepositoryImpl @Inject constructor(
    private val baseUrl: String,
    private val httpClient: HttpClient
) : PostRepository {

    override suspend fun fetchPostById(postId: String): ResourceState<Post> {
        val call = httpClient.request("$baseUrl/posts/$postId") {
            method = HttpMethod.Get
        }.call

        if (call.response.status.value == 200) {
            return try {
                ResourceState.success(call.response.body())
            } catch (e: Exception) {
                ResourceState.error(e.message.toString(), null)
            }
        }
        return ResourceState.error(call.response.status.description, null)
    }

    companion object {
        private const val TAG = "PostRepository"
    }
}