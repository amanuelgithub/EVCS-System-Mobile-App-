package com.amanuel.evscsystem.data.repository

import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.network.AuthApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi, // this dependency is provided by the NetworkModule
    private val userPreferences: UserPreferences,
) : BaseRepository() {

    suspend fun login(email: String, password: String) = safeApiCall {
        api.login(email, password)
    }

    suspend fun saveAuthToken(token: String) {
        userPreferences.saveAuthToken(token)
    }

}