package com.amanuel.evscsystem.data.repository

import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.network.AuthApi
import com.amanuel.evscsystem.fcm.FirebaseCloudMessagingService

class AuthRepository(
    private val api: AuthApi,
    private val userPreferences: UserPreferences,
) : BaseRepository() {

    suspend fun login(email: String, password: String) = safeApiCall {
        api.login(email, password)
    }

    suspend fun saveAuthToken(token: String) {
        userPreferences.saveAuthToken(token)
    }

}