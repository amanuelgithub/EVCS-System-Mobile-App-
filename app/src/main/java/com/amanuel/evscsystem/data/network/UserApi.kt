package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.responses.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.*

interface UserApi {

    @GET("users")
    suspend fun getUsers(): LoginResponse // needs modification


    // updates the fcm_registration token of the device
    @PUT("devices/{id}/")
    suspend fun updateFCMToken(
        @Path("id") id: Int,
        @Body fcm_token: String
    ): Any
}