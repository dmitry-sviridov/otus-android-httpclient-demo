package ru.otus.httpclientsdemo.mock

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*

object PostMockApi {

    fun mockClient() = HttpClient(MockEngine) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    "/posts/777" -> respond("", HttpStatusCode.NotFound, request.headers)
                    "/posts/666" -> respond(
                        invalidResponse(),
                        HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                    else -> respond(
                        successResponse(request.url.pathSegments[2].toLong(), request.url.pathSegments[2].toLong()),
                        HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
            }
        }
    }

    private fun successResponse(userId: Long, postId: Long) =
        """
            {"userId":$userId,"id":$postId,"title": "unittest","body": "unittest"}
        """.trimIndent()

    private fun invalidResponse() =
        """
            {"usr":500,"idPost":1,"name":"unittest","message":"unittest"}
        """.trimIndent()
}