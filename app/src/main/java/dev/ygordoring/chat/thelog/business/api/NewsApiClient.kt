package dev.ygordoring.chat.thelog.business.api

import dev.ygordoring.chat.thelog.business.vo.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApiClient {
    @GET("data.json")
    fun fetchNewsListAsync(
        @QueryMap params: Map<String, String>
    ): Deferred<Response<NewsResponse>>
}