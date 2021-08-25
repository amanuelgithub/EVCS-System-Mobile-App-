package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.db.models.Report
import com.amanuel.evscsystem.data.db.models.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.*

interface UserApi {


    // get a user by a specific id
    @GET("users/{pk}")
    suspend fun getUser(@Path("pk") id: Int): User

    // updates the fcm_registration token of the device
//    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @PUT("devices/{id}/")
    suspend fun updateFCMToken(
        @Path("id") id: Int,
        @Field("fcm_token") fcm_token: String
    ): Any


    // used to update the profile of the user
    @FormUrlEncoded
    @PATCH("users/{id}")
    fun updateProfile(
        @Path("id") userId: Int,
        @Field("id") id: Int,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,

        ): Call<User>


    @FormUrlEncoded
    @PATCH("change-password/")
    fun changePassword(
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String
    ): Call<Any>


    @POST("records/{id}/report/")
    fun report(@Path("id") id: Int, @Body report: Report): Call<Report>
}