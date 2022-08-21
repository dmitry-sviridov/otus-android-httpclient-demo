package ru.otus.httpclientsdemo.repository

import ru.otus.httpclientsdemo.domain.ResourceState
import ru.otus.httpclientsdemo.model.Post
import javax.inject.Inject

interface PostRepository {
    suspend fun fetchPostById(postId: String): ResourceState<Post>
}

class PostRepositoryImpl @Inject constructor(val api: PostsApi) : PostRepository {

    override suspend fun fetchPostById(postId: String): ResourceState<Post> {
        val response = api.fetchPosts(postId)
        if (response.isSuccessful) {
            return ResourceState.success(response.body())
        }
        return ResourceState.error(
            response.errorBody()?.string() ?: "something went wrong",
            null
        )
    }
}