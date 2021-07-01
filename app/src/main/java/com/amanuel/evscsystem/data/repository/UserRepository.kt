package com.amanuel.evscsystem.data.repository

import com.amanuel.evscsystem.data.network.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi, // this dependency is provided by the NetworkModule
) : BaseRepository() {


    suspend fun getUser() = safeApiCall {
        api.getUsers()
    }

}