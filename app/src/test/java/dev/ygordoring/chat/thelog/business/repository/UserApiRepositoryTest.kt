package dev.ygordoring.chat.thelog.business.repository

import dev.ygordoring.chat.thelog.business.storage.StorageContract
import dev.ygordoring.chat.thelog.business.vo.UserVO
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UserApiRepositoryTest {

    private lateinit var storageContract: StorageContract

    private lateinit var userApiRepository: UserRepositoryContract

    @Before
    fun setUp() {
        storageContract = mock()

        userApiRepository = UserApiRepository(storageContract)
    }

    @Test
    fun `Create user account, when save data happens successfully, then return success status`() {

        // ARRANGE

        val user = UserVO("Nome", "email@email.com", "abc123")
        val expectedStatus = true

        whenever(userApiRepository.signUp(user))
            .thenReturn(expectedStatus)

        // ACT

        val result = userApiRepository.signUp(user)

        // ASSERT

        assertEquals(expectedStatus, result)
    }
}