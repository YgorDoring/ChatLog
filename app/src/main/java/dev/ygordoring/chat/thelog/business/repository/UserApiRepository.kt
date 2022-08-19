package dev.ygordoring.chat.thelog.business.repository

import dev.ygordoring.chat.thelog.business.storage.StorageContract
import dev.ygordoring.chat.thelog.business.vo.UserVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class UserApiRepository(val prefs: StorageContract) : UserRepositoryContract {
    companion object {
        const val KEY_USER_LIST = "KEY_USER_LIST"
        const val KEY_CURRENT_USER = "KEY_CURRENT_USER"
    }

    override fun signUp(user: UserVO): Boolean {
        try {
            val userList = this.getList()
            for (item in userList) {
                if (item.email == user.email) {
                    throw Exception("Usuário já cadastrado!")
                }
            }
            user.id = UUID.randomUUID().toString()
            userList.add(user)

            this.saveList(userList);
            return true

        } catch (e: Exception) {
            throw e
        }
    }

    override fun login(email: String, password: String): Boolean {
        try {
            for (user in this.getList()) {
                if (user.email == email && user.password == password) {
                    this.setCurrentUser(user)

                    return true
                }
            }
            return false
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getUser(): UserVO? {
        val userJson = prefs.getString(KEY_CURRENT_USER)
        if (!userJson.isNullOrEmpty()) {
            return Gson().fromJson(userJson, UserVO::class.java)
        }
        return null
    }

    override fun logout(): Boolean {
        prefs.saveString(KEY_CURRENT_USER, "")
        return true
    }

    private fun setCurrentUser(user: UserVO) {
        prefs.saveString(KEY_CURRENT_USER, Gson().toJson(user))
    }

    private fun saveList(list: List<UserVO>) {
        prefs.saveString(KEY_USER_LIST, Gson().toJson(list))
    }

    private fun getList(): MutableList<UserVO> {
        val listJson = prefs.getString(KEY_USER_LIST)
        if (listJson != null) {
            return Gson().fromJson(
                prefs.getString(KEY_USER_LIST),
                object : TypeToken<List<UserVO>>() {}.type
            )
        }
        return mutableListOf()
    }
}
