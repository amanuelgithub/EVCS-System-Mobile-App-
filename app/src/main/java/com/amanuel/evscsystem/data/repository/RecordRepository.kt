package com.amanuel.evscsystem.data.repository

import androidx.room.withTransaction
import com.amanuel.evscsystem.data.AppDatabase
import com.amanuel.evscsystem.data.network.RecordApi
import com.amanuel.evscsystem.utilities.networkBoundResource
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordApi: RecordApi,
    private val appDatabase: AppDatabase
) {

    private val recordDao = appDatabase.recordDao()

    fun getRecords() = networkBoundResource(
        query = {
            recordDao.getRecords()
        },
        fetch = {
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