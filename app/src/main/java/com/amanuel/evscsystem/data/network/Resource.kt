package com.amanuel.evscsystem.data.network

import okhttp3.ResponseBody

// a class to hold the response form the api requests

// this sealed class is used to wrap the api responses and handle success and failure properly
sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
            val isNetworkError: Boolean,
            val errorCode: Int?,
            val errorBody: ResponseBody?
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}