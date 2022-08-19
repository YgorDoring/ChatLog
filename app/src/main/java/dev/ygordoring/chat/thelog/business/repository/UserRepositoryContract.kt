package dev.ygordoring.chat.thelog.business.repository

import dev.ygordoring.chat.thelog.business.vo.UserVO

interface UserRepositoryContract {
    fun signUp(user: UserVO): Boolean

    fun login(email: String, password: String): Boolean

    fun getUser(): UserVO?

    fun logout(): Boolean
}