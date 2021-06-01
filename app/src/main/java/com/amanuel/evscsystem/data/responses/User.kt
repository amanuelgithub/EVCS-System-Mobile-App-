package com.amanuel.evscsystem.data.responses

data class User(
        val id: Int,
        val name: String,
        val email: String,
        val access_token: String?, // making this nullable may affect the previous implementation
        val created_at: String,
        val updated_at: String,
        val email_verified_at: Any
)