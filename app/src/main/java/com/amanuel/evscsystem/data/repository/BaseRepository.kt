package com.amanuel.evscsystem.data.repository

import com.amanuel.evscsystem.data.network.Resource
import com.amanuel.evscsystem.data.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Resource.Failure(throwable, apiCall.invoke())
            }
        }
    }


    suspend fun logout(api: UserApi) = safeApiCall {
        api.logout()
    }

    // since sending the fcm token can be done any where
}