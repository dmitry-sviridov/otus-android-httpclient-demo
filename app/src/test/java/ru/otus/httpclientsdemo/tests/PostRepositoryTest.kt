package ru.otus.httpclientsdemo.tests

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import ru.otus.httpclientsdemo.domain.ResourceState
import ru.otus.httpclientsdemo.model.Post
import ru.otus.httpclientsdemo.repository.PostRepository
import ru.otus.httpclientsdemo.repository.PostRepositoryImpl
import ru.otus.httpclientsdemo.mock.PostMockApi

class PostRepositoryTest {

    private val repository: PostRepository = PostRepositoryImpl("", PostMockApi.mockClient())

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
            assertEquals("Not Found", response.message)
            assertNull(response.data)
        }
    }

    @Test
    fun fetchInvalidPostTest() {
        runBlocking {
            val response = repository.fetchPostById("666")
            assertEquals(ResourceState.FetchingStatus.ERROR, response.status)
            assertEquals(true, response.message?.contains("Unexpected JSON token"))
            assertNull(response.data)
        }
    }
}