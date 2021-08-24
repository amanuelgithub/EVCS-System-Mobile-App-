package com.amanuel.evscsystem.ui.auth

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.repository.AuthRepository
import com.amanuel.evscsystem.data.repository.UserRepository
import com.amanuel.evscsystem.data.responses.LoginResponse
import com.amanuel.evscsystem.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : BaseViewModel(authRepository) {

//    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
//    val loginResponse: LiveData<Resource<LoginResponse>>
//        get() = _loginResponse

    // todo: update the Any response to Actual response from the sever
    private val _updateFCMTokenResponse: MutableLiveData<Resource<Any>> = MutableLiveData()
    val updateFCMTokenResponse: LiveData<Resource<Any>>
        get() = _updateFCMTokenResponse

//    fun login(email: String, password: String) = viewModelScope.launch {
//        _loginResponse.value = Resource.Loading(null)
//        _loginResponse.value = authRepository.login(email, password)
//    }

    fun login(view: View, email: String, password: String, onResult: (LoginResponse?) -> Unit){
        authRepository.login(view, email, password, onResult)
    }

    fun updateFCMToken(id: Int, fcmToken: String) = viewModelScope.launch {
        _updateFCMTokenResponse.value = userRepository.updateFCMToken(id, fcmToken)
    }

    suspend fun saveAuthToken(token: String) {
        authRepository.saveAuthToken(token)
    }

    suspend fun saveUserId(userId: Int) {
        userRepository.saveUserId(userId)
    }

//    suspend fun saveFCMTokenToPreferences(fcmToken: String) {
//        authRepository.saveFCMTokenToPreferences(fcmToken)
//    }
}