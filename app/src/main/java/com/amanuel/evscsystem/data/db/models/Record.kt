package com.amanuel.evscsystem.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("pk") val pk: Int,
    @SerializedName("vehicle") val vehiclePlateNumber: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("vehicle_speed") val vehicleSpeed: Int,
    @SerializedName("status") val status: Boolean,
    @SerializedName("address") val address: String,
    @SerializedName("created_at") val createdAt: String
)