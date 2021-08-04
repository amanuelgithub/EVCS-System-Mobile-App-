package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.db.models.Notification
import retrofit2.http.GET

interface NotificationApi {

    @GET("notifications")
    suspend fun getNotifications(): List<Notification>


}