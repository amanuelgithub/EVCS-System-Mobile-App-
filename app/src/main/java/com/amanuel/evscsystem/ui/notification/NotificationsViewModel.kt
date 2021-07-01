package com.amanuel.evscsystem.ui.notification

import androidx.lifecycle.*
import com.amanuel.evscsystem.data.models.Notification
import com.amanuel.evscsystem.data.models.NotificationDao
import com.amanuel.evscsystem.data.network.NotificationApi
import com.amanuel.evscsystem.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject internal constructor(
    repository: NotificationRepository
) : ViewModel() {
    // Note: this .asLiveData() does multiple things including launching a coroutine.
    val notifications = repository.getNotifications().asLiveData()

}