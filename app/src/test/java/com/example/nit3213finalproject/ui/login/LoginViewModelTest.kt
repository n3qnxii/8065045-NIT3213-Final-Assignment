package com.example.nit3213finalproject.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nit3213finalproject.data.model.DashboardResponse
import com.example.nit3213finalproject.data.model.LoginRequest
import com.example.nit3213finalproject.data.model.LoginResponse
import com.example.nit3213finalproject.data.remote.ApiService
import com.example.nit3213finalproject.data.repository.AppRepository
import com.example.nit3213finalproject.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule =
        InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule =
        MainDispatcherRule()

    private class FakeApiService : ApiService {

        override suspend fun login(
            loginRequest: LoginRequest
        ): Response<LoginResponse> {

            return if (
                loginRequest.username == "8065045" &&
                loginRequest.password == "Wongsakorn"
            ) {

                Response.success(
                    LoginResponse(
                        keypass = "travel"
                    )
                )

            } else {

                Response.error(
                    401,
                    okhttp3.ResponseBody.create(
                        null,
                        "Unauthorized"
                    )
                )
            }
        }

        override suspend fun getDashboard(
            keypass: String
        ): Response<DashboardResponse> {

            return Response.success(
                DashboardResponse(
                    entities = emptyList(),
                    entityTotal = 0
                )
            )
        }
    }

    private fun createViewModel(): LoginViewModel {

        val apiService =
            FakeApiService()

        val repository =
            AppRepository(apiService)

        return LoginViewModel(repository)
    }

    @Test
    fun login_emptyStudentId_returnsError() {

        val viewModel =
            createViewModel()

        viewModel.login(
            studentId = "",
            firstName = "Wongsakorn"
        )

        assertEquals(
            "Please enter your student ID.",
            viewModel.error.value
        )
    }

    @Test
    fun login_studentIdWithLetters_returnsError() {

        val viewModel =
            createViewModel()

        viewModel.login(
            studentId = "s8065045",
            firstName = "Wongsakorn"
        )

        assertEquals(
            "Student ID must contain numbers only, without 's'.",
            viewModel.error.value
        )
    }

    @Test
    fun login_emptyFirstName_returnsError() {

        val viewModel =
            createViewModel()

        viewModel.login(
            studentId = "8065045",
            firstName = ""
        )

        assertEquals(
            "Please enter your first name.",
            viewModel.error.value
        )
    }

    @Test
    fun login_validCredentials_returnsKeypass() {

        val viewModel =
            createViewModel()

        viewModel.login(
            studentId = "8065045",
            firstName = "Wongsakorn"
        )

        assertEquals(
            "travel",
            viewModel.keypass.value
        )

        assertFalse(
            viewModel.loading.value ?: true
        )
    }
}