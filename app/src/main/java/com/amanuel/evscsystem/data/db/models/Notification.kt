package com.amanuel.evscsystem.data.db.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

// Note: the Parcelable Interface makes it easy to send
// the object between different fragments
@Entity
@Parcelize
data class Notification(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val notification_id: Int,
    val record_id: Int,
    val latitude: Double,
    val longtude: Double,
    val plate_number: String,
    val vehicle_speed: Int,
    val Location: String?, // must be removed
    val createdAt: Long? = System.currentTimeMillis(),
) : Parcelable {
    val createFormattedDate: String
        get() = DateFormat.getDateTimeInstance().format(createdAt)
}