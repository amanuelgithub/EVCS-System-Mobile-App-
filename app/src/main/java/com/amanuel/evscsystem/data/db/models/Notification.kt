package com.amanuel.evscsystem.data.db.models

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
@Entity
@Parcelize
data class Notification(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val plateNumber: String,
    val speed: Int,
    val Location: String,
    val createdAt: Long? = System.currentTimeMillis(),
) : Parcelable {
    val createFormattedDate: String
        get() = DateFormat.getDateTimeInstance().format(createdAt)
}