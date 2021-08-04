package com.amanuel.evscsystem.data.responses

import com.amanuel.evscsystem.data.db.models.User

data class LoginResponse(
        val key: String,
        val user: User
)