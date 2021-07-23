package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.responses.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.*

interface AuthApi {

    @FormUrlEncoded
    @POST("login/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse


    // api/rest-auth/
    @POST("logout/")
    suspend fun logout(): ResponseBody

}