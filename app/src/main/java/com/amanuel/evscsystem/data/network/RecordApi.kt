package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.db.models.Record
import retrofit2.http.GET
import retrofit2.http.Headers

interface RecordApi {

//    @Headers("Content-Type: application/json")
    @Headers("Accept: application/json")
    @GET("records/?format=json")
    suspend fun getRecords(): List<Record>

}