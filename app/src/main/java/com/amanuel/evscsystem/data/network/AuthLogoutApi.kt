package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.responses.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST

interface AuthLogoutApi {
    // api/rest-auth/
//    @POST("logout/")
//    suspend fun logout(): ResponseBody

    @POST("logout/")
    fun logout(): Call<ResponseBody>

}