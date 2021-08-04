package com.amanuel.evscsystem.data.repository

import androidx.room.withTransaction
import com.amanuel.evscsystem.data.AppDatabase
import com.amanuel.evscsystem.data.SessionManager
import com.amanuel.evscsystem.data.db.UserDao
import com.amanuel.evscsystem.data.db.UserLoginDao
import com.amanuel.evscsystem.data.db.models.User
import com.amanuel.evscsystem.data.db.models.UserLogin
import com.amanuel.evscsystem.data.network.UserApi
import com.amanuel.evscsystem.utilities.networkBoundResource
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi, // this dependency is provided by the NetworkModule
    private val appDatabase: AppDatabase,
    private val sessionManager: SessionManager
) : BaseRepository() {

    private val userDao = appDatabase.userDao()
    private val userLoginDao = appDatabase.userLoginDao()

    fun getUser(userId: Int, authToken: String) = networkBoundResource<UserLogin, User>(
        query = {
            userLoginDao.getUserLoginData(userId)
        },
        fetch = {
            userApi.getUser(userId)
        },
        saveFetchResult = { user ->
            appDatabase.withTransaction {
                userDao.deleteUser()
                userDao.insert(user)

                val userLogin = UserLogin(user.pk, true, user)

                userLoginDao.deleteUserLogin()
                userLoginDao.insert(userLogin)
            }
        }
    )

    suspend fun updateFCMToken(id: Int, fcmToken: String) = safeApiCall {
        userApi.updateFCMToken(id, fcmToken)
    }

    fun saveUserId(userId: Int) {
        sessionManager.saveUserId(userId)
    }

    //    suspend fun getUser() = safeApiCall {
    //        userApi.getUsers()
    //    }
}