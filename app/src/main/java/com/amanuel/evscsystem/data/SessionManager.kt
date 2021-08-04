package com.amanuel.evscsystem.data

import android.content.Context
import android.content.SharedPreferences
import com.amanuel.evscsystem.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
    }

    // Function to save auth token
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun saveUserId(userId: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, userId)
        editor.apply()
    }

    // Function to fetch auth token
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchUserId(): Int {
        return prefs.getInt(USER_ID, 0)
    }
}