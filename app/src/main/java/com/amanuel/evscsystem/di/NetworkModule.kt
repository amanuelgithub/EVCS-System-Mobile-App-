package com.amanuel.evscsystem.di

import com.amanuel.evscsystem.data.UserPreferences
import com.amanuel.evscsystem.data.network.AuthApi
import com.amanuel.evscsystem.data.network.NotificationApi
import com.amanuel.evscsystem.data.network.RemoteServiceBuilderHelper
import com.amanuel.evscsystem.data.network.UserApi
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
        remoteServiceBuilderHelper: RemoteServiceBuilderHelper,
        userPreferences: UserPreferences?,
    ): AuthApi {
        /** Note: actually we don't need an auth token in the AuthApi. but for safety purposes i included it. */
        val token = runBlocking{userPreferences?.authToken?.first()}
        return remoteServiceBuilderHelper.buildAuthApi(
            AuthApi::class.java,
            token
        )
    }


    // api/
    @Singleton
    @Provides
    fun provideUserService(
        remoteServiceBuilderHelper: RemoteServiceBuilderHelper,
        userPreferences: UserPreferences?
    ): UserApi {
        val token = runBlocking{userPreferences?.authToken?.first()}

        return remoteServiceBuilderHelper.buildApi(
            UserApi::class.java,
            token
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












