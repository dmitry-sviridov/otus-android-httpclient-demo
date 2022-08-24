package ru.otus.httpclientsdemo.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class PostApiMockDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/posts/777" -> MockResponse()
                .setResponseCode(404)
                .setBody("")
            "/posts/666" -> MockResponse()
                .setResponseCode(200)
                .setBody("{\"usr\":500,\"idPost\":1,\"name\":\"unittest\",\"message\":\"unittest\"}")
            "/posts/555" -> MockResponse()
                .setResponseCode(200)
                .setBody("{\"usr\":500\"idPost\"1,\"name\":\"unittest\",\"messae:\"unittest\"}")
            else -> MockResponse()
                .setResponseCode(200)
                .setBody(
                    "{\"userId\":${
                        request.path?.split("/")?.get(2)
                    },\"id\":${
                        request.path?.split("/")?.get(2)
                    },\"title\": \"unittest\",\"body\": \"unittest\"}"
                )
        }
    }
}