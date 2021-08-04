package com.amanuel.evscsystem.data.db

import androidx.room.*
import com.amanuel.evscsystem.data.db.models.UserLogin
import kotlinx.coroutines.flow.Flow

@Dao
interface UserLoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userLogin: UserLogin)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(userLogin: UserLogin)

    @Query("SELECT * FROM UserLogin WHERE userId = :userId")
    fun getUserLoginData(userId: Int): Flow<UserLogin>

    @Query("DELETE FROM UserLogin")
    fun deleteUserLogin()

}