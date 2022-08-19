package dev.ygordoring.chat.thelog.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.ygordoring.chat.thelog.business.repository.UserRepositoryContract
import dev.ygordoring.chat.thelog.business.vo.UserVO
import dev.ygordoring.chat.thelog.getOrAwaitValueTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {
    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userRepositoryMock: UserRepositoryContract
    private lateinit var viewModel: SplashViewModel

    private lateinit var isAuthenticatedLiveDataObserver: Observer<Boolean>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        userRepositoryMock = mock()

        isAuthenticatedLiveDataObserver = mock()

        viewModel = SplashViewModel(
            Dispatchers.IO,
            userRepositoryMock
        )
    }

    @Test
    fun `Check user authentication, when it is authenticated, then return true`() {

        // ARRANGE
        val user = UserVO("Nome", "email@email.com", "abc123")
        val expectedResultResponse = true

        whenever(userRepositoryMock.getUser())
            .thenReturn(user)

        // ACT
        viewModel.checkAuthentication()
        viewModel.isAuthenticated.observeForever(isAuthenticatedLiveDataObserver)

        val resultFromLiveData = viewModel.isAuthenticated.getOrAwaitValueTest()

        // ASSERT
        verify(isAuthenticatedLiveDataObserver).onChanged(resultFromLiveData)
        kotlin.test.assertEquals(resultFromLiveData, expectedResultResponse)
    }

    @Test
    fun `Check user authentication, when it is not authenticated, then return false`() {

        // ARRANGE
        val user = null
        val expectedResultResponse = false

        whenever(userRepositoryMock.getUser())
            .thenReturn(user)

        // ACT
        viewModel.checkAuthentication()
        viewModel.isAuthenticated.observeForever(isAuthenticatedLiveDataObserver)

        val resultFromLiveData = viewModel.isAuthenticated.getOrAwaitValueTest()

        // ASSERT
        verify(isAuthenticatedLiveDataObserver).onChanged(resultFromLiveData)
        kotlin.test.assertEquals(resultFromLiveData, expectedResultResponse)
    }
}