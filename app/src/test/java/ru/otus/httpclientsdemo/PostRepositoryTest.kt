package ru.otus.httpclientsdemo

import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.BeforeClass
import org.junit.Test
import ru.otus.httpclientsdemo.domain.ResourceState
import ru.otus.httpclientsdemo.mock.PostApiMockDispatcher
import ru.otus.httpclientsdemo.repository.PostRepository
import ru.otus.httpclientsdemo.repository.PostRepositoryImpl

class PostRepositoryTest {

    private val repository: PostRepository =
        PostRepositoryImpl("http://localhost:${server.port}", Gson(), OkHttpClient())

    companion object {

        lateinit var server: MockWebServer

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            server = MockWebServer()
            server.dispatcher = PostApiMockDispatcher()
            server.start()
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            server.shutdown()
        }
    }

    @Test
    fun fetchExistingPostTest() {
        runBlocking {
            val response = repository.fetchPostById("100")
            assertEquals(ResourceState.FetchingStatus.SUCCESS, response.status)
            assertEquals(100L, response.data?.id)
            assertEquals(100L, response.data?.userId)
            assertEquals("unittest", response.data?.body)
            assertEquals("unittest", response.data?.title)
            assertNull(response.message)
        }
    }

    @Test
    fun fetchNonExistingPostTest() {
        runBlocking {
            val response = repository.fetchPostById("777")
            assertEquals(ResourceState.FetchingStatus.ERROR, response.status)
            assertEquals("Client Error", response.message)
            assertNull(response.data)
        }
    }

    @Test
    fun fetchInvalidPostObjectTest() {
        runBlocking {
            val response = repository.fetchPostById("666")
            assertEquals(ResourceState.FetchingStatus.SUCCESS, response.status)
            assertEquals(0L,  response.data?.id)
            assertEquals(0L, response.data?.userId)
            assertEquals(null, response.data?.title)
            assertEquals(null, response.data?.body)
            assertNull(response.message)
        }
    }

    @Test
    fun fetchInvalidJsonSchemaObjectTest() {
        runBlocking {
            val response = repository.fetchPostById("555")
            assertEquals(ResourceState.FetchingStatus.ERROR, response.status)
            assertEquals(true, response.message?.contains("MalformedJsonException"))
            assertNull(response.data)
        }
    }
}