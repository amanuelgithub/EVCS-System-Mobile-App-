package com.amanuel.evscsystem.data.repository

import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.models.User
import com.amanuel.evscsystem.data.network.AuthApi
import com.amanuel.evscsystem.data.network.AuthLogoutApi
import com.amanuel.evscsystem.data.responses.Device
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authLogoutApi: AuthLogoutApi,
    private val authApi: AuthApi, // this dependency is provided by the NetworkModule
//    private val userPreferences: UserPreferences,
    private val sessionManager: SessionManager
) : BaseRepository() {

    suspend fun login(email: String, password: String) = safeApiCall {
        authApi.login(email, password)
    }

    suspend fun saveAuthToken(token: String) {
        sessionManager.saveAuthToken(token)
    }

//    suspend fun saveFCMTokenToPreferences(fcmToken: String) {
//        userPreferences.saveFCMToken(fcmToken)
//    }

    suspend fun logout() = safeApiCall {
        authLogoutApi.logout()
    }
}