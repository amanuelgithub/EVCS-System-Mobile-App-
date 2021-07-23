package com.amanuel.evscsystem.data.repository

import androidx.lifecycle.LiveData
import com.amanuel.evscsystem.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    // This function only allow fetching from a remote resource (using retrofit).
    // But in case of 'NetworkBoundResource' a caching mechanism is implemented
    // that will handle when to fetch from a remote resource and store it
    // to a local sqlite database using room
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Resource.Failure(throwable, apiCall.invoke())
            }
        }
    }

//    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
//        return withContext(Dispatchers.IO) {
//            try {
//                Resource.Success(apiCall.invoke())
//            } catch (throwable: Throwable) {
//                when (throwable) {
//                    is HttpException -> {
//                        Resource.Failure(
//                            false,
//                            throwable.code(),
//                            throwable.response()?.errorBody()
//                        )
//                    }
//                    else -> {
//                        Resource.Failure(true, null, null)
//                    }
//                }
//            }
//        }
//    }

}