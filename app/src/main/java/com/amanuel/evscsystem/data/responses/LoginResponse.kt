package com.amanuel.evscsystem.data.responses

import com.amanuel.evscsystem.data.models.User

data class LoginResponse(
        val key: String,
        val user: User
)