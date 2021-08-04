package com.amanuel.evscsystem.di

import android.content.Context
import com.amanuel.evscsystem.data.AppDatabase
import com.amanuel.evscsystem.data.db.NotificationDao
import com.amanuel.evscsystem.data.db.UserDao
import com.amanuel.evscsystem.data.db.UserLoginDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideUserLogin(appDatabase: AppDatabase): UserLoginDao {
        return appDatabase.userLoginDao()
    }

    @Provides
    fun provideNotificationDao(appDatabase: AppDatabase): NotificationDao {
        return appDatabase.notificationDao()
    }

}