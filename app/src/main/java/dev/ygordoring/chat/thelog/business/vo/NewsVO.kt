package dev.ygordoring.chat.thelog.business.vo

import java.io.Serializable

data class NewsVO(
    val user: UserVO,
    val message: MessageVO,
) : Serializable