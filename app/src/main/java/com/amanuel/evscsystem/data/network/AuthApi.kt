package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.responses.LoginResponse
import retrofit2.http.*

interface AuthApi {

    @FormUrlEncoded
    @POST("login/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    // updates the fcm_registration token of the device
    @PATCH("http://10.240.72.29:8000/RecordReport/devices/{id}/")
    suspend fun updateFCMToken(
        @Path("id") id: Int,
        @Body fcm_token: String
    ): Any

}