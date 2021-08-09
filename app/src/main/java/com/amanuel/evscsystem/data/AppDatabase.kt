package com.amanuel.evscsystem.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amanuel.evscsystem.data.db.models.Notification
import com.amanuel.evscsystem.data.db.NotificationDao
import com.amanuel.evscsystem.data.db.RecordDao
import com.amanuel.evscsystem.data.db.UserDao
import com.amanuel.evscsystem.data.db.UserLoginDao
import com.amanuel.evscsystem.data.db.models.Record
import com.amanuel.evscsystem.data.db.models.User
import com.amanuel.evscsystem.data.db.models.UserLogin
import com.amanuel.evscsystem.utilities.constants.Constants

@Database(
    entities = [
        Notification::class,
        Record::class,
        User::class,
        UserLogin::class,
    ], version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun notificationDao(): NotificationDao

    abstract fun recordDao(): RecordDao

    abstract fun userDao(): UserDao

    abstract fun userLoginDao(): UserLoginDao




    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
                .build()
        }


//        // Create and pre-populate the database
//        private fun buildDatabase(context: Context): AppDatabase {
//            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
////                .fallbackToDestructiveMigration()
//                .addCallback(
//                    object : RoomDatabase.Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
//                                .setInputData(workDataOf(KEY_FILENAME to NOTIFICATION_DATA_FILENAME))
//                                .build()
//                            WorkManager.getInstance(context).enqueue(request)
//                        }
//                    }
//                )
//                .build()
//        }
    }
}