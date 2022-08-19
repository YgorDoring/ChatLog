package dev.ygordoring.chat.thelog.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ygordoring.chat.thelog.business.repository.UserRepositoryContract
import dev.ygordoring.chat.thelog.utils.isValidEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
    private val coroutineContext: CoroutineContext,
    private val userRepository: UserRepositoryContract
) : ViewModel() {
    private val coroutineScope = CoroutineScope(coroutineContext)

    val loginResult = MutableLiveData<Boolean>()
    val isValidForm = MutableLiveData<Boolean>()
    val errorEmail = MutableLiveData<Boolean>()
    val errorPassword = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun checkValidFormData(email: String, password: String) {
        errorEmail.value = !email.isValidEmail()
        errorPassword.value = password.isEmpty()

        isValidForm.value = (!errorEmail.value!! && !errorPassword.value!!)
    }

    fun login(email: String, password: String) {
        coroutineScope.launch {
            try {
                loginResult.postValue(userRepository.login(email, password))
            } catch (e: Exception) {
                error.postValue(e.message)
            }
        }
    }
}