package dev.ygordoring.chat.thelog.business.vo

import java.io.Serializable
import java.util.*

data class PostVO(
    var id: String,
    var content: String,
    val createdAt: Date,
    val user: UserVO,
) : Serializable {
    override fun toString(): String {
        return "id: $id, content: $content, createdAt: $createdAt, userId: ${user.id}, userEmail: ${user.email}"
    }
}