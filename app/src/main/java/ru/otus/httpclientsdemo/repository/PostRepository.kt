package ru.otus.httpclientsdemo.repository

import retrofit2.Response
import ru.otus.httpclientsdemo.model.Post
import javax.inject.Inject

interface PostRepository {
    suspend fun fetchPostById(postId: String): Response<Post>
}

class PostRepositoryImpl @Inject constructor(val api: PostsApi): PostRepository {

    override suspend fun fetchPostById(postId: String): Response<Post> {
        return api.fetchPosts(postId)
    }
}