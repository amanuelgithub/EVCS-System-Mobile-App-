package com.amanuel.evscsystem.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.amanuel.evscsystem.data.AppDatabase
import com.amanuel.evscsystem.data.models.Notification
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeedDatabaseWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters){
    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null){
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        // find the type of the particular instance
                        val notifyType = object : TypeToken<List<Notification>>() {}.type
                        val notificationList: List<Notification> = Gson().fromJson(jsonReader, notifyType)

                        val database = AppDatabase.getInstance(applicationContext)

                        database.notificationDao().insertAll(notificationList)

                        Result.success()
                    }

                }
            }else{
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        }catch (ex: Exception){
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object{
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME = "NOTIFICATION_DATA_FILENAME"
    }
}