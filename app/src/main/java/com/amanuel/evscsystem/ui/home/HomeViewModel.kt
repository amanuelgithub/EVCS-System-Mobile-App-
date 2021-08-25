package com.amanuel.evscsystem.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amanuel.evscsystem.data.db.models.User
import com.amanuel.evscsystem.data.db.models.UserLogin
import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.repository.NotificationRepository
import com.amanuel.evscsystem.data.repository.UserRepository
import com.amanuel.evscsystem.data.responses.LoginResponse
import com.amanuel.evscsystem.ui.base.BaseViewModel
import com.amanuel.evscsystem.ui.notification.SortOrder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository
) : BaseViewModel(userRepository) {


    private val _updateFCMTokenResponse: MutableLiveData<Resource<Any>> = MutableLiveData()
    val updateFCMTokenResponse: LiveData<Resource<Any>>
        get() = _updateFCMTokenResponse

    fun loginUser(userId: Int, authToken: String): LiveData<Resource<UserLogin>> =
        userRepository.getUser(userId, authToken).asLiveData()

    fun updateFCMToken(id: Int, fcmToken: String) = viewModelScope.launch {
        _updateFCMTokenResponse.value = userRepository.updateFCMToken(id, fcmToken)
    }

    val searchQuery = MutableStateFlow("")

    val sortOrder = MutableStateFlow(SortOrder.BY_DATE)

    private val notificationsFlow = combine(
        searchQuery,
        sortOrder,
    ) { query, sortOrder ->
        Pair(query, sortOrder)
    }.flatMapLatest { (query, sortOrder) ->
        notificationRepository.getNotifications(query, sortOrder)
    }

    // Note: this .asLiveData() does multiple things including launching a coroutine.
    val notifications = notificationsFlow.asLiveData()
}
