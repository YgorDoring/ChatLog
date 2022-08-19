package dev.ygordoring.chat.thelog.business.repository

import dev.ygordoring.chat.thelog.business.storage.StorageContract
import dev.ygordoring.chat.thelog.business.vo.PostVO
import dev.ygordoring.chat.thelog.business.vo.UserVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class PostApiRepository(val prefs: StorageContract) : PostRepositoryContract {
    companion object {
        const val KEY_POST_LIST = "KEY_POST_LIST_"
    }

    override fun fetchList(user: UserVO): MutableList<PostVO> {
        try {
            val list = mutableListOf<PostVO>()

            list.addAll(this.getList(user.id))

            list.addAll(this.getFakeList())

            return list
        } catch (e: Exception) {
            throw e
        }
    }

    override fun create(postVO: PostVO): PostVO {
        val list = this.getList(postVO.user.id)

        postVO.id = UUID.randomUUID().toString()

        list.add(0, postVO)

        this.saveList(postVO.user.id, list)

        return postVO
    }

    override fun update(postVO: PostVO): Boolean {
        try {
            val list = this.getList(postVO.user.id)

            list.forEachIndexed { index, item ->
                if (item.id == postVO.id) {
                    list[index] = postVO
                }
            }

            this.saveList(postVO.user.id, list);
            return true

        } catch (e: Exception) {
            throw e
        }
    }

    override fun delete(userId: String, postId: String): Boolean {
        try {
            val list = this.getList(userId)

            var index = -1
            list.forEachIndexed { idx, item ->
                if (item.id == postId) {
                    index = idx
                    return@forEachIndexed
                }
            }
            if (index >= 0) {
                list.removeAt(index)
            }
            this.saveList(userId, list);
            return true

        } catch (e: Exception) {
            throw e
        }
    }

    private fun saveList(userId: String, list: List<PostVO>) {
        prefs.saveString(this.getKey(userId), Gson().toJson(list))
    }

    private fun getList(userId: String): MutableList<PostVO> {
        val prefsKey = this.getKey(userId)
        val listJson = prefs.getString(prefsKey)

        if (listJson != null) {
            return Gson().fromJson(
                prefs.getString(prefsKey),
                object : TypeToken<List<PostVO>>() {}.type
            )
        }
        return mutableListOf()
    }

    private fun getKey(userId: String): String {
        return (KEY_POST_LIST + userId)
    }

    private fun getFakeList(): MutableList<PostVO> {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        val user1 = UserVO("Linda", "")
        val user2 = UserVO("Camila", "")
        val list = mutableListOf<PostVO>()
        list.add(
            PostVO(
                "123",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse tristique ligula vel turpis ornare viverra. Pellentesque nec turpis sit amet diam dignissim dapibus at vitae leo.",
                sdf.parse("2022-08-19 19:55")!!,
                user1
            )
        )
        list.add(
            PostVO(
                "124",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse tristique ligula vel turpis ornare viverra.",
                sdf.parse("2022-08-18 19:55")!!,
                user2
            )
        )
        list.add(
            PostVO(
                "125",
                "Suspendisse tristique ligula vel turpis ornare viverra. Pellentesque nec turpis sit amet diam dignissim dapibus at vitae leo.",
                sdf.parse("2022-08-15 19:55")!!,
                user2
            )
        )
        list.add(
            PostVO(
                "126",
                "Pellentesque nec turpis sit amet diam dignissim dapibus at vitae leo. Suspendisse tristique ligula vel turpis ornare viverra. Pellentesque nec turpis sit amet diam dignissim dapibus at vitae leo.",
                sdf.parse("2022-07-18 21:12")!!,
                user1
            )
        )
        return list
    }
}
