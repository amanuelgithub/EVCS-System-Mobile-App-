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
import javax.inject.Singleton

/**
 * Created By: Amanuel Girma
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAuthService(
        remoteServiceBuilderHelper: RemoteServiceBuilderHelper,
        userPreferences: UserPreferences?,
    ): AuthApi{
        /** Note: actually we don't need an auth token in the AuthApi. but for safety purposes i included it. */
        return remoteServiceBuilderHelper.buildApi(AuthApi::class.java)
    }


    @Singleton
    @Provides
    fun provideUserService(
        remoteServiceBuilderHelper: RemoteServiceBuilderHelper,
        userPreferences: UserPreferences?
    ) : UserApi{
        return remoteServiceBuilderHelper.buildApi(UserApi::class.java, userPreferences?.authToken.toString())
    }


    @Singleton
    @Provides
    fun provideNotificationService(
        remoteServiceBuilderHelper: RemoteServiceBuilderHelper,
    ) : NotificationApi{
        return remoteServiceBuilderHelper.buildApi(NotificationApi::class.java)
    }

}












