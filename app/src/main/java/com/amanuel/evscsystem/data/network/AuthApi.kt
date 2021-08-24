package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

//    @FormUrlEncoded
//    @POST("login/")
//    suspend fun login(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): LoginResponse

    @FormUrlEncoded
    @POST("login/")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>


}