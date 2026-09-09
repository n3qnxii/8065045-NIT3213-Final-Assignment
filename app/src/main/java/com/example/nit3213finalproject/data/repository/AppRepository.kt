package com.example.nit3213finalproject.data.repository

import com.example.nit3213finalproject.data.model.DashboardResponse
import com.example.nit3213finalproject.data.model.LoginRequest
import com.example.nit3213finalproject.data.model.LoginResponse
import com.example.nit3213finalproject.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun login(
        username: String,
        password: String
    ): Response<LoginResponse> {

        val request = LoginRequest(
            username = username,
            password = password
        )

        return apiService.login(request)
    }

    suspend fun getDashboard(
        keypass: String
    ): Response<DashboardResponse> {

        return apiService.getDashboard(keypass)
    }
}