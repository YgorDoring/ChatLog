package dev.ygordoring.chat.thelog.business.repository

import dev.ygordoring.chat.thelog.business.api.NewsApiClient
import dev.ygordoring.chat.thelog.business.vo.MessageVO
import dev.ygordoring.chat.thelog.business.vo.NewsResponse
import dev.ygordoring.chat.thelog.business.vo.NewsVO
import dev.ygordoring.chat.thelog.business.vo.UserVO
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.*
import kotlin.test.assertEquals

class NewsApiRepositoryTest {
    private lateinit var newsApiClient: NewsApiClient
    private lateinit var newsRepositoryMock: NewsApiRepository

    @Before
    fun setUp() {
        newsApiClient = mock()
        newsRepositoryMock = NewsApiRepository(newsApiClient)
    }

    @Test
    fun `Fetch list of news, when it is requested, then return deferred list successfully`() {

        // ARRANGE
        val newsList = listOf(
            NewsVO(UserVO("User1", "http://image"), MessageVO("Message1", Date())),
            NewsVO(UserVO("User2", "http://image"), MessageVO("Message2", Date())),
            NewsVO(UserVO("User3", "http://image"), MessageVO("Message3", Date())),
        )
        val expectedNewsResponse = NewsResponse(
            newsList
        )

        val expectedResponse = Response.success(expectedNewsResponse)

        val expectedDeferredResponse = CompletableDeferred(expectedResponse)

        whenever(newsRepositoryMock.fetchNewsListAsync()).thenReturn(
            expectedDeferredResponse
        )

        // ACT

        val dataResponse = runBlocking { newsRepositoryMock.fetchNewsListAsync().await() }

        // ASSERT

        assertEquals(expectedResponse, dataResponse)
    }
}
