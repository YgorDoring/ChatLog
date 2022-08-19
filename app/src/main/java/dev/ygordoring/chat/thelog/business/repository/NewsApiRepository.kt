package dev.ygordoring.chat.thelog.business.repository

import dev.ygordoring.chat.thelog.business.api.NewsApiClient
import dev.ygordoring.chat.thelog.business.vo.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

class NewsApiRepository(
    private val newsApiClient: NewsApiClient
) : NewsRepositoryContract {

    override fun fetchNewsListAsync(): Deferred<Response<NewsResponse>> {
        try {
            val params = HashMap<String, String>()
            params["Content-Type"] = "application/json"

            return newsApiClient.fetchNewsListAsync(params)

        } catch (e: Exception) {
            throw e
        }
    }
}
