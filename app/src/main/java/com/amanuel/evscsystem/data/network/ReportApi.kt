package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.data.db.models.Report
import retrofit2.Call
import retrofit2.http.*

interface ReportApi {

    @POST("records/{id}/report/")
    fun report(@Path("id") id: Int, @Body report: Report): Call<Report>
}