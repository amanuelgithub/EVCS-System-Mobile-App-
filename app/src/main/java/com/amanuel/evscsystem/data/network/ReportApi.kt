package com.amanuel.evscsystem.data.network

import retrofit2.http.*

interface ReportApi {

    @POST("records/{id}/report/")
    suspend fun updateFCMToken(
        @Path("id") id: Int,
        
    ): Any
}