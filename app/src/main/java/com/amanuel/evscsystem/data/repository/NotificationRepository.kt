package com.amanuel.evscsystem.data.repository

import androidx.room.withTransaction
import com.amanuel.evscsystem.data.AppDatabase
import com.amanuel.evscsystem.data.db.models.Notification
import com.amanuel.evscsystem.data.network.NotificationApi
import com.amanuel.evscsystem.ui.notification.SortOrder
import com.amanuel.evscsystem.utilities.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationApi: NotificationApi,
    private val appDatabase: AppDatabase
) {

    val notificationDao = appDatabase.notificationDao()

    fun getNotifications(searchQuery: String, sortOrder: SortOrder) = networkBoundResource<List<Notification>, List<Notification>>(
        query = {
            notificationDao.getNotifications(searchQuery, sortOrder)
        },
        fetch = {
            delay(2000)
            notificationApi.getNotifications()
        },
        saveFetchResult = { notifications ->
            appDatabase.withTransaction {
                notificationDao.deleteAll()
                notificationDao.insertAll(notifications)
            }
        }
    )

}