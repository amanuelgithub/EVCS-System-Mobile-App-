package com.amanuel.evscsystem.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amanuel.evscsystem.data.db.models.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Query("SELECT * FROM Record")
    fun getRecords(): Flow<List<Record>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(records: List<Record>)

    @Query("DELETE FROM Record")
    suspend fun deleteAll()

}