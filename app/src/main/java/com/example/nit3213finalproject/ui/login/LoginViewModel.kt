package com.example.nit3213finalproject.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213finalproject.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _keypass = MutableLiveData<String?>()
    val keypass: LiveData<String?> = _keypass

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(
        studentId: String,
        firstName: String
    ) {

        if (studentId.isBlank()) {
            _error.value =
                "Please enter your student ID."
            return
        }

        if (!studentId.matches(Regex("^\\d+$"))) {
            _error.value =
                "Student ID must contain numbers only, without 's'."
            return
        }

        if (firstName.isBlank()) {
            _error.value =
                "Please enter your first name."
            return
        }

        _loading.value = true
        _error.value = null

        viewModelScope.launch {

            try {

                val response = repository.login(
                    username = studentId.trim(),
                    password = firstName.trim()
                )

                if (response.isSuccessful) {

                    val returnedKeypass =
                        response.body()?.keypass

                    if (!returnedKeypass.isNullOrBlank()) {

                        _keypass.value =
                            returnedKeypass

                    } else {

                        _error.value =
                            "Login succeeded but no keypass was returned."
                    }

                } else {

                    _error.value =
                        when (response.code()) {

                            400 ->
                                "Invalid student ID or first name."

                            401 ->
                                "Incorrect student ID or first name."

                            404 ->
                                "Login endpoint was not found."

                            500 ->
                                "Server error. Please try again."

                            else ->
                                "Login failed. Error code: ${response.code()}"
                        }
                }

            } catch (e: Exception) {

                _error.value =
                    "Unable to connect to the server. Please check your internet connection."

            } finally {

                _loading.value = false
            }
        }
    }
}