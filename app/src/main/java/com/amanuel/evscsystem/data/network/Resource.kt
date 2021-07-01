package com.amanuel.evscsystem.data.network

// a class to hold the response form the api requests
// this sealed class is used to wrap the api responses and handle success and failure properly
sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Failure<T>(throwable: Throwable, data: T? = null) : Resource<T>(data, throwable)
}
