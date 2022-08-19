package dev.ygordoring.chat.thelog.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ygordoring.chat.thelog.business.repository.PostRepositoryContract
import dev.ygordoring.chat.thelog.business.repository.UserRepositoryContract
import dev.ygordoring.chat.thelog.business.vo.PostVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class PostViewModel(
    private val coroutineContext: CoroutineContext,
    private val userRepository: UserRepositoryContract,
    private val postRepository: PostRepositoryContract
) : ViewModel() {
    private val coroutineScope = CoroutineScope(coroutineContext)

    val isSaveSuccess = MutableLiveData<Boolean>()
    val isValidForm = MutableLiveData<Boolean>()
    val errorContent = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun checkValidFormData(content: String) {
        errorContent.value = content.isEmpty() || content.length < 2

        isValidForm.value = (!errorContent.value!!)
    }

    fun save(post: PostVO?, content: String) {
        coroutineScope.launch {
            try {
                val user = userRepository.getUser()

                val success: Boolean
                if (post != null) {
                    post.content = content
                    success = postRepository.update(post)

                } else {
                    postRepository.create(
                        PostVO(
                            "",
                            content,
                            Date(),
                            user!!
                        )
                    )
                    success = true
                }
                isSaveSuccess.postValue(success)

            } catch (e: Exception) {
                error.postValue(e.message)
            }
        }
    }
}