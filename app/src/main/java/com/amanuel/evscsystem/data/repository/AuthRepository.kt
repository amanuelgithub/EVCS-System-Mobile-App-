package com.amanuel.evscsystem.data.repository

import android.view.View
import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.data.db.models.Report
import com.amanuel.evscsystem.data.network.AuthApi
import com.amanuel.evscsystem.data.network.AuthLogoutApi
import com.amanuel.evscsystem.data.responses.LoginResponse
import com.amanuel.evscsystem.utilities.showErrorSnackBar
import com.amanuel.evscsystem.utilities.showWarningSnackBar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authLogoutApi: AuthLogoutApi,
    private val authApi: AuthApi, // this dependency is provided by the NetworkModule
//    private val userPreferences: UserPreferences,
    private val sessionManager: SessionManager
) : BaseRepository() {

//    suspend fun login(email: String, password: String) = safeApiCall {
//        authApi.login(email, password)
//    }

    fun login(view: View, email: String, password: String, onResult: (LoginResponse?) -> Unit){
        authApi.login(email, password).enqueue(
            object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    if (t is IOException) {
                        view.showErrorSnackBar("This is an actual network failure: ${t.localizedMessage}")
                    }
                    if (t is HttpException) {
                        when {
                            t.code() == 401 -> {
                                view.showWarningSnackBar("An Authorized Access!!")
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

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val loginResponse = response.body()
                    onResult(loginResponse)
                }
            }
        )
    }

    fun deleteAuthToken(){
        sessionManager.deleteAuthToken()
    }

    fun deleteUserId(){
        sessionManager.deleteUserId()
    }

    suspend fun saveAuthToken(token: String) {
        sessionManager.saveAuthToken(token)
    }

//    suspend fun saveFCMTokenToPreferences(fcmToken: String) {
//        userPreferences.saveFCMToken(fcmToken)
//    }

//    suspend fun logout() = safeApiCall {
//        authLogoutApi.logout()
//    }

    fun logout(view: View, onResult: (ResponseBody?) -> Unit) {
        authLogoutApi.logout().enqueue(
            object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    if (t is IOException) {
                        view.showErrorSnackBar("This is an actual network failure: ${t.localizedMessage}")
                    }
                    if (t is HttpException) {
                        when {
                            t.code() == 401 -> {
                                view.showWarningSnackBar("An Authorized Access!!")
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

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val responseBody = response.body()
                    onResult(responseBody)
                }
            }
        )
    }

}