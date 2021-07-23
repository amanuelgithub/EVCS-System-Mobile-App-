package com.amanuel.evscsystem.data.responses

import com.amanuel.evscsystem.data.models.User

data class Device(
    val user_id: Int,
    val fcm_token: String

)