package com.amanuel.evscsystem.data.responses

data class User(
        val pk: Int,
        val username: String,
        val email: String,
        val first_name: String,
        val last_name: String
//        val id: Int,
//        val name: String,
//        val email: String,
//        val access_token: String?, // making this nullable may affect the previous implementation
//        val fcm_token: String?, // holds device's unique_id
//        val created_at: String,
//        val updated_at: String,
//        val email_verified_at: Any
)