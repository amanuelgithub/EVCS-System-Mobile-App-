package com.amanuel.evscsystem.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amanuel.evscsystem.data.db.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: User)

    @Query("SELECT * FROM User WHERE pk = :userId")
    fun getUserData(userId: Int): LiveData<User>

    //    @Delete
    @Query("DELETE FROM User")
    fun deleteUser()

}