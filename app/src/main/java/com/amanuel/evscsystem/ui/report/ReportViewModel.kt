package com.amanuel.evscsystem.ui.report

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.amanuel.evscsystem.data.db.models.Report
import com.amanuel.evscsystem.data.network.ReportApi
import com.amanuel.evscsystem.utilities.showErrorSnackBar
import com.amanuel.evscsystem.utilities.showWarningSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import javax.inject.Inject
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportApi: ReportApi
) : ViewModel() {

    fun report(
        view: View,
        recordId: Int,
        report: Report,
        onResult: (Report?) -> Unit
    ) {
        reportApi.report(recordId, report).enqueue(
            object : Callback<Report> {
                override fun onFailure(call: Call<Report>, t: Throwable) {

                    if (t is IOException) {
                        view.showErrorSnackBar("This is an actual network failure: ${t.localizedMessage}")
                    }
                    if (t is HttpException) {
                        when {
                            t.code() == 401 -> {
                                view.showWarningSnackBar("Unauthorized Access!!")
                            }
                            else -> {
                                view.showErrorSnackBar("This is an actual network failure: ${t.localizedMessage}")
                            }
                        }
                    } else {
                        view.showErrorSnackBar("conversion issue! big problems :( ${t.localizedMessage}")
                    }
                    onResult(null)
                }

                override fun onResponse(call: Call<Report>, response: Response<Report>) {
                    val reportedReport = response.body()
                    onResult(reportedReport)
                }
            }
        )
    }
}