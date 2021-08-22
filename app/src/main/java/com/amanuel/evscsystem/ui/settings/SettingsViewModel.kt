package com.amanuel.evscsystem.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.repository.AuthRepository
import com.amanuel.evscsystem.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel(){


    private val _updateFCMTokenResponse: MutableLiveData<Resource<Any>> = MutableLiveData()
    val updateFCMTokenResponse: LiveData<Resource<Any>>
        get() = _updateFCMTokenResponse


    fun logout() = viewModelScope.launch {
        authRepository.deleteAuthToken()
        authRepository.deleteUserId()

        authRepository.logout()
//        userPreferences.clear()
        // todo: create a way to go back to the login page of the application
    }

    fun deleteAuthToken(){
        authRepository.deleteAuthToken()
    }

    fun deleteUserId(){
        authRepository.deleteUserId()
    }


    fun updateFCMToken(id: Int, fcmToken: String) = viewModelScope.launch {
        _updateFCMTokenResponse.value = userRepository.updateFCMToken(id, fcmToken)
    }
}