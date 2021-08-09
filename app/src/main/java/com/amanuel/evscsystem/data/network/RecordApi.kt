package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.db.models.Record
import retrofit2.http.GET
import retrofit2.http.Headers

interface RecordApi {

    @Headers("Accept: application/json")
    @GET("records")
    suspend fun getRecords(): List<Record>
}