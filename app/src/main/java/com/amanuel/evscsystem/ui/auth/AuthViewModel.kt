package com.amanuel.evscsystem.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amanuel.evscsystem.data.models.User
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.repository.AuthRepository
import com.amanuel.evscsystem.data.responses.Device
import com.amanuel.evscsystem.data.responses.LoginResponse
import com.amanuel.evscsystem.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    // todo: update the Any response to Actual response from the sever
    private val _updateFCMTokenResponse: MutableLiveData<Resource<Any>> = MutableLiveData()
    val updateFCMTokenResponse: LiveData<Resource<Any>>
        get() = _updateFCMTokenResponse

    fun login(email: String, password: String) = viewModelScope.launch {
//        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
    }

    fun updateFCMToken(id: Int, fcmToken: String) = viewModelScope.launch {
        _updateFCMTokenResponse.value = repository.updateFCMToken(id, fcmToken)
    }

    suspend fun saveAuthToken(token: String) {
        repository.saveAuthToken(token)
    }

    suspend fun saveFCMTokenToPreferences(fcmToken: String) {
        repository.saveFCMTokenToPreferences(fcmToken)
    }
}