package com.amanuel.evscsystem.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.repository.AuthRepository
import com.amanuel.evscsystem.data.responses.LoginResponse
import com.amanuel.evscsystem.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    // network call
    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)

    }

    suspend fun saveAuthToken(token: String) {
        repository.saveAuthToken(token)
    }
}