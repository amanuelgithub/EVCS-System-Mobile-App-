package com.amanuel.evscsystem.di

import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.network.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

/**
 * Created By: Amanuel Girma
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // api/rest-auth/
    @Singleton
    @Provides
    fun provideAuthService(
        remoteServiceBuilderHelper: RemoteServiceBuilderHelper
    ): AuthApi {
        /** Note: actually we don't need an auth token in the AuthApi. but for safety purposes i included it. */
        return remoteServiceBuilderHelper.buildAuthApi(
            AuthApi::class.java
        )
    }

    // api/rest-auth/
    @Singleton
    @Provides
    fun provideAuthLogoutService(
        remoteServiceBuilderHelper: RemoteServiceBuilderHelper,
//        userPreferences: UserPreferences?
        sessionManager: SessionManager
    ): AuthLogoutApi {
//        var token: String? = runBlocking { userPreferences?.authToken?.first() }

        return remoteServiceBuilderHelper.buildAuthLogoutApi(
            AuthLogoutApi::class.java,
            sessionManager.fetchAuthToken()
        )
    }


    // api/
    @Singleton
    @Provides
    fun provideUserService(
        remoteServiceBuilderHelper: RemoteServiceBuilderHelper,
//        userPreferences: UserPreferences?
        sessionManager: SessionManager
    ): UserApi {
//        var token: String? = runBlocking { userPreferences?.authToken?.first() }

        return remoteServiceBuilderHelper.buildApi(
            UserApi::class.java,
            sessionManager.fetchAuthToken()
        )
    }


    @Singleton
    @Provides
    fun provideNotificationService(
        remoteServiceBuilderHelper: RemoteServiceBuilderHelper,
    ): NotificationApi {
        return remoteServiceBuilderHelper.buildAuthApi(NotificationApi::class.java)
    }

}












