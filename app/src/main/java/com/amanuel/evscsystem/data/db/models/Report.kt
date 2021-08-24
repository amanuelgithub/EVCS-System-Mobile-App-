package com.amanuel.evscsystem.data.db.models

import com.google.gson.annotations.SerializedName

data class Report(
    @SerializedName("is_drunk") val isDrunk: Boolean,
    @SerializedName("is_using_cell_phone") val isUsingCelPhone: Boolean,
    @SerializedName("is_not_having_license") val isNotHavingLicense: Boolean,
    @SerializedName("is_chewing_chat") val isChewingChat: Boolean,
    @SerializedName("other_violation") val otherViolation: String,
    @SerializedName("description") val description: String,

    // measures taken
    @SerializedName("payment_amount") val paymentAmount: Double,
    @SerializedName("short_summary") val shortSummary: String,
)