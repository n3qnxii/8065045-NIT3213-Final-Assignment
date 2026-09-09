package com.example.nit3213finalproject.ui.dashboard

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
class DashboardViewModelTest {

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

            return Response.success(
                LoginResponse(
                    keypass = "travel"
                )
            )
        }

        override suspend fun getDashboard(
            keypass: String
        ): Response<DashboardResponse> {

            val entities = listOf(
                mapOf<String, Any>(
                    "destination" to "Paris",
                    "country" to "France",
                    "bestSeason" to "Spring",
                    "popularAttraction" to "Eiffel Tower",
                    "description" to "Capital city of France"
                ),
                mapOf<String, Any>(
                    "destination" to "Tokyo",
                    "country" to "Japan",
                    "bestSeason" to "Spring",
                    "popularAttraction" to "Senso-ji Temple",
                    "description" to "Capital city of Japan"
                )
            )

            return Response.success(
                DashboardResponse(
                    entities = entities,
                    entityTotal = entities.size
                )
            )
        }
    }

    @Test
    fun loadDashboard_returnsEntities() {

        val apiService = FakeApiService()

        val repository =
            AppRepository(apiService)

        val viewModel =
            DashboardViewModel(repository)

        viewModel.loadDashboard(
            "travel"
        )

        val entities =
            viewModel.entities.value

        assertEquals(
            2,
            entities?.size
        )
    }

    @Test
    fun loadDashboard_returnsCorrectEntityTotal() {

        val apiService = FakeApiService()

        val repository =
            AppRepository(apiService)

        val viewModel =
            DashboardViewModel(repository)

        viewModel.loadDashboard(
            "travel"
        )

        assertEquals(
            2,
            viewModel.entityTotal.value
        )
    }

    @Test
    fun loading_isFalse_afterDashboardLoaded() {

        val apiService = FakeApiService()

        val repository =
            AppRepository(apiService)

        val viewModel =
            DashboardViewModel(repository)

        viewModel.loadDashboard(
            "travel"
        )

        assertFalse(
            viewModel.loading.value ?: true
        )
    }
}