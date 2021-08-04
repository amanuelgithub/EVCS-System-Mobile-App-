package com.amanuel.evscsystem.data.db.models

import androidx.room.Embedded
import androidx.room.Entity

@Entity(primaryKeys = ["userId"])
data class UserLogin(
    val userId: Int,
    val login: Boolean = false, // by default no user is logged in
    @Embedded
    val user: User
)
