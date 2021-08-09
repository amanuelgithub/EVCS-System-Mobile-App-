package com.amanuel.evscsystem.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record(
    @PrimaryKey
    val id: Int,
    val vehicle_speed: Int,
    val duration: String,
//    val vehicleId: Int,
    val vehicle: Int,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val created_at: String
)