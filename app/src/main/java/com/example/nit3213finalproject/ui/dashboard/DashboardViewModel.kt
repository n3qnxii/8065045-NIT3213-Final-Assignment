package com.example.nit3213finalproject.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213finalproject.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _entities =
        MutableLiveData<List<Map<String, Any>>>()

    val entities: LiveData<List<Map<String, Any>>> =
        _entities

    private val _entityTotal =
        MutableLiveData<Int>()

    val entityTotal: LiveData<Int> =
        _entityTotal

    private val _loading =
        MutableLiveData(false)

    val loading: LiveData<Boolean> =
        _loading

    private val _error =
        MutableLiveData<String?>()

    val error: LiveData<String?> =
        _error

    fun loadDashboard(keypass: String) {

        if (keypass.isBlank()) {
            _error.value = "Invalid keypass."
            return
        }

        _loading.value = true
        _error.value = null

        viewModelScope.launch {

            try {

                val response =
                    repository.getDashboard(keypass)

                if (response.isSuccessful) {

                    val body = response.body()

                    if (body != null) {

                        _entities.value =
                            body.entities

                        _entityTotal.value =
                            body.entityTotal

                    } else {

                        _error.value =
                            "No dashboard data was returned."
                    }

                } else {

                    _error.value =
                        "Unable to load dashboard. Error code: ${response.code()}"
                }

            } catch (e: Exception) {

                _error.value =
                    "Unable to connect to the server."

            } finally {

                _loading.value = false
            }
        }
    }
}