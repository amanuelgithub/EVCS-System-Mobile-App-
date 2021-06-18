package com.amanuel.evscsystem.ui.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amanuel.evscsystem.data.models.NotificationDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject internal constructor(
    // pass the notifications repository but for simplicity we take rooms NotificationDao
    private val notificationsDao: NotificationDao
) : ViewModel() {

    val notifications = notificationsDao.getNotifications().asLiveData()
}