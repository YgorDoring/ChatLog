package dev.ygordoring.chat.thelog.business.repository

import dev.ygordoring.chat.thelog.business.vo.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface NewsRepositoryContract {
    fun fetchNewsListAsync(): Deferred<Response<NewsResponse>>
}