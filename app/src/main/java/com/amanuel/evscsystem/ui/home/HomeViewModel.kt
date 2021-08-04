package com.amanuel.evscsystem.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amanuel.evscsystem.data.db.models.User
import com.amanuel.evscsystem.data.db.models.UserLogin
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.repository.UserRepository
import com.amanuel.evscsystem.data.responses.LoginResponse
import com.amanuel.evscsystem.ui.base.BaseViewModel
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel(userRepository) {


    private val _updateFCMTokenResponse: MutableLiveData<Resource<Any>> = MutableLiveData()
    val updateFCMTokenResponse: LiveData<Resource<Any>>
        get() = _updateFCMTokenResponse

    fun loginUser(userId: Int, authToken: String): LiveData<Resource<UserLogin>> =
        userRepository.getUser(userId, authToken).asLiveData()

    fun updateFCMToken(id: Int, fcmToken: String) = viewModelScope.launch {
        _updateFCMTokenResponse.value = userRepository.updateFCMToken(id, fcmToken)
    }

}