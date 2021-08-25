package com.amanuel.evscsystem.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amanuel.evscsystem.data.db.models.Record
import com.amanuel.evscsystem.ui.record.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    fun getRecords(searchQuery: String = "", sortOrder: SortOrder): Flow<List<Record>> =
        when(sortOrder){
            SortOrder.BY_DATE -> getRecordsSortedByDateCreated(searchQuery)
            SortOrder.BY_PLATE_NUMBER -> getRecordsSortedByName(searchQuery)
        }

    @Query("SELECT * FROM Record WHERE vehiclePlateNumber LIKE '%' || :searchQuery || '%' ORDER BY createdAt")
    fun getRecordsSortedByDateCreated(searchQuery: String = ""): Flow<List<Record>>

    @Query("SELECT * FROM Record WHERE vehiclePlateNumber LIKE '%' || :searchQuery || '%' ORDER BY vehiclePlateNumber")
    fun getRecordsSortedByName(searchQuery: String = ""): Flow<List<Record>> // name here is similar to plateNumber



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(records: List<Record>)

    @Query("DELETE FROM Record")
    suspend fun deleteAll()

}