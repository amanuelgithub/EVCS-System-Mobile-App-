package com.amanuel.evscsystem.data.network

import com.amanuel.evscsystem.BuildConfig
import com.amanuel.evscsystem.utilities.constants.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RemoteServiceBuilderHelper @Inject constructor() {

    // api/rest-auth/login/
    fun <Api> buildAuthApi(api: Class<Api>): Api {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_AUTH_LOGIN_URL)
            .client(OkHttpClient.Builder()
//                .connectTimeout(5, TimeUnit.MINUTES)
//                .connectTimeout(5, TimeUnit.MINUTES)
                .addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder().also {
//                        it.addHeader("Authorization", "token $authToken")
                    }.build())
                }.also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(api)
    }


    // api/...
    fun <Api> buildApi(api: Class<Api>, token: String? = null): Api {
        val gson = GsonBuilder()
            .setLenient()
            .create()


        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("Authorization", "token $token")
                    }.build())
                }.also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(api)
    }


}