package com.amanuel.evscsystem.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amanuel.evscsystem.data.db.models.Notification
import com.amanuel.evscsystem.ui.notification.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {


    fun getNotifications(searchQuery: String = "", sortOrder: SortOrder): Flow<List<Notification>> =
        when(sortOrder){
            SortOrder.BY_DATE -> getNotificationsSortedByDateCreated(searchQuery)
            SortOrder.BY_PLATE_NUMBER -> getNotificationsSortedByName(searchQuery)
        }

    @Query("SELECT * FROM Notification WHERE plate_number LIKE '%' || :searchQuery || '%' ORDER BY createdAt")
    fun getNotificationsSortedByDateCreated(searchQuery: String = ""): Flow<List<Notification>>

    @Query("SELECT * FROM Notification WHERE plate_number LIKE '%' || :searchQuery || '%' ORDER BY plate_number")
    fun getNotificationsSortedByName(searchQuery: String = ""): Flow<List<Notification>> // name here is similar to plateNumber

//    @Query("SELECT * FROM Notification")
//    fun getNotifications(): Flow<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: Notification)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notificationList: List<Notification>)

    @Query("DELETE FROM Notification")
    suspend fun deleteAll()
}