package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.db.models.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface UserApi {


    // get a user by a specific id
    @GET("users/{pk}")
    suspend fun getUser(@Path("pk") id: Int): User


//    @GET("users")
//    suspend fun getUsers(): LoginResponse // needs modification

    // updates the fcm_registration token of the device
//    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @PUT("devices/{id}/")
    suspend fun updateFCMToken(
        @Path("id") id: Int,
        @Field("fcm_token") fcm_token: String
    ): Any
}