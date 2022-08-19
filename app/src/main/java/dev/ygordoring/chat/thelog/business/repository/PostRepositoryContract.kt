package dev.ygordoring.chat.thelog.business.repository

import dev.ygordoring.chat.thelog.business.vo.PostVO
import dev.ygordoring.chat.thelog.business.vo.UserVO

interface PostRepositoryContract {
    fun fetchList(user: UserVO): MutableList<PostVO>

    fun create(postVO: PostVO): PostVO

    fun update(postVO: PostVO): Boolean

    fun delete(userId: String, postId: String): Boolean
}