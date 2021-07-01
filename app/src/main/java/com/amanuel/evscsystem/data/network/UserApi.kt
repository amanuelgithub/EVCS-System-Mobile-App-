package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.responses.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @GET("users")
    suspend fun getUsers(): LoginResponse // needs modification

//    @GET("users/{id}")
//    suspend fun getUser(@Path("id") userId: Int): LoginResponse

    @POST("logout")
    suspend fun logout(): ResponseBody
}