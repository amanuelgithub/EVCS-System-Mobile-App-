package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.db.models.Notification
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface NotificationApi {

    @Headers("Accept: application/json")
    @GET("notification/?format=json")
    suspend fun getNotifications(): List<Notification>
}