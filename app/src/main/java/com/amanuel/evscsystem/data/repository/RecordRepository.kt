package com.amanuel.evscsystem.data.repository

import androidx.room.withTransaction
import com.amanuel.evscsystem.data.AppDatabase
import com.amanuel.evscsystem.data.network.RecordApi
import com.amanuel.evscsystem.ui.record.SortOrder
import com.amanuel.evscsystem.utilities.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordApi: RecordApi,
    private val appDatabase: AppDatabase
) {

    private val recordDao = appDatabase.recordDao()

    fun getRecords(searchQuery: String, sortOrder: SortOrder) = networkBoundResource(
        query = {
            recordDao.getRecords(searchQuery, sortOrder)
        },
        fetch = {
            delay(2000)
            recordApi.getRecords()
        },
        saveFetchResult = { records ->
            appDatabase.withTransaction {
                recordDao.deleteAll()
                recordDao.insertAll(records)
            }
        }
    )

}