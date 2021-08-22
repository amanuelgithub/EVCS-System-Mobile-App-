package com.amanuel.evscsystem.data.repository

import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.data.network.AuthApi
import com.amanuel.evscsystem.data.network.AuthLogoutApi
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

    suspend fun logout() = safeApiCall {
        authLogoutApi.logout()
    }
}