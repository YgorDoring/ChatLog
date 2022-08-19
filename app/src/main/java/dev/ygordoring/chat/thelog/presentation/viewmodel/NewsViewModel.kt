package dev.ygordoring.chat.thelog.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ygordoring.chat.thelog.business.repository.NewsRepositoryContract
import dev.ygordoring.chat.thelog.business.vo.NewsVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NewsViewModel(
    private val coroutineContext: CoroutineContext,
    private val newsRepository: NewsRepositoryContract
) : ViewModel() {
    private val coroutineScope = CoroutineScope(coroutineContext)

    val newsList = MutableLiveData<List<NewsVO>>()
    val error = MutableLiveData<String>()

    fun fetchNewsList() {
        coroutineScope.launch {
            try {
                val response = newsRepository.fetchNewsListAsync().await()

                if (response.isSuccessful) {
                    response.body()?.let {
                        val list = it.news
                        newsList.postValue(list)
                    }
                } else {
                    error.postValue("Failed result")
                }

            } catch (e: Exception) {
                Log.e(javaClass.simpleName, e.message, e)

                error.postValue(e.message)
            }
        }
    }
}