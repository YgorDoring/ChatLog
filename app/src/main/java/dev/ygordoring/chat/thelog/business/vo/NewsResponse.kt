package dev.ygordoring.chat.thelog.business.vo

import java.io.Serializable

data class NewsResponse(
    val news: List<NewsVO>
) : Serializable