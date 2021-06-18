package com.amanuel.evscsystem.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

/**
 * Created By: Amanuel Girma
 */

// Note: the Parcelable Interface makes it easy to send
// the object between different fragments
@Entity(tableName = "notifications_table")
@Parcelize
data class Notification(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val plateNumber: Int,
    val speed: Int,
    val location: String,
    val createdAt: Long = System.currentTimeMillis(),
) : Parcelable {
    val createFormattedDate: String
        get() = DateFormat.getDateTimeInstance().format(createdAt)
}