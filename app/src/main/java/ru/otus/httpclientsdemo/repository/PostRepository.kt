package ru.otus.httpclientsdemo.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gildor.coroutines.okhttp.await
import ru.otus.httpclientsdemo.domain.ResourceState
import ru.otus.httpclientsdemo.model.Post
import javax.inject.Inject

interface PostRepository {
    suspend fun fetchPostById(postId: String): ResourceState<Post>
}

class PostRepositoryImpl @Inject constructor(
    private val baseUrl: String,
    private val gson: Gson,
    private val okHttpClient: OkHttpClient
) : PostRepository {

    override suspend fun fetchPostById(postId: String): ResourceState<Post> {
        val request = Request.Builder()
            .get()
            .addHeader("Content-Type", "application/json")
            .url("$baseUrl/posts/$postId")
            .build()

        val response = okHttpClient.newCall(request).await()
        if (response.isSuccessful) {
            return try {
                val post = gson.fromJson(response.body?.string(), Post::class.java)
                ResourceState.success(post)
            } catch (e: JsonSyntaxException) {
                Log.d(TAG, "fetchPostById: $e")
                ResourceState.error(e.message.toString(), null)
            }
        }
        return ResourceState.error(response.message, null)
    }

    companion object {
        private const val TAG = "PostRepository"
    }
}