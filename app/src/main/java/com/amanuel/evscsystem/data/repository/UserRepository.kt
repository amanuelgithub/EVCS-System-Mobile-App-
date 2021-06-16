package com.amanuel.evscsystem.data.repository

import com.amanuel.evscsystem.data.network.UserApi

class UserRepository(
    private val api: UserApi,
) : BaseRepository() {


    suspend fun getUser() = safeApiCall {
        api.getUser()
    }

}