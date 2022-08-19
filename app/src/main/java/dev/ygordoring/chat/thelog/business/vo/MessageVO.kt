package dev.ygordoring.chat.thelog.business.vo

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class MessageVO(
    val content: String,
    @SerializedName("created_at") val createdAt: Date,
) : Serializable