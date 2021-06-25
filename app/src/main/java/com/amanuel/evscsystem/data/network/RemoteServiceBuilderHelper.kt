package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteServiceBuilderHelper @Inject constructor() {

    companion object {
        //        private const val BASE_URL = "http://simplifiedcoding.tech/mywebapp/public/api/"
//        private const val BASE_URL = "http://apix.simplifiedcoding.in/api/"
//        private const val BASE_URL = "http://10.240.72.198:8000/api/rest-auth/"
        private const val BASE_URL = "http://localhost:3000"
    }


    fun <Api> buildApi(
        api: Class<Api>,
        token: String? = null
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("Authorization", "Bearer $token")
                    }.build())
                }.also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}