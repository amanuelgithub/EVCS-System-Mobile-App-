package com.amanuel.evscsystem.data.network

import okhttp3.ResponseBody
import retrofit2.http.POST

interface AuthLogoutApi {
    // api/rest-auth/
    @POST("logout/")
    suspend fun logout(): ResponseBody

}