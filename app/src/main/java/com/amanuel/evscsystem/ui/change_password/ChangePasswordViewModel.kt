package com.amanuel.evscsystem.ui.change_password

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amanuel.evscsystem.data.db.models.User
import com.amanuel.evscsystem.data.network.UserApi
import com.amanuel.evscsystem.data.repository.UserRepository
import com.amanuel.evscsystem.utilities.showErrorSnackBar
import com.amanuel.evscsystem.utilities.showWarningSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val userApi: UserApi,
    private val userRepository: UserRepository
) : ViewModel(){


    fun changePassword(
        view: View,
        oldPassword: String,
        newPassword: String,
        onResult: (Any?) -> Unit
    ) {
        userApi.changePassword(oldPassword, newPassword).enqueue(
            object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {

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

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    val reportedReport = response.body()
                    onResult(reportedReport)
                }
            }
        )
    }
}