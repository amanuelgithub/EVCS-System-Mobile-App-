package com.amanuel.evscsystem.data

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import androidx.preference.PreferenceManager
import com.amanuel.evscsystem.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject


/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {

    // This is used to set up a default preference
//    private var defaultPref: SharedPreferences =
//        PreferenceManager.getDefaultSharedPreferences(context)

    private var customPrefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val LANGUAGE = "en"

        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
    }

    ///////
    // an extension function that will open shared preferences editor, call a function to set the values
    // for the different types and finally apply the them
    inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit){
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    // extension function to set preference values
    // it uses the edit extension function of the SharedPreferences
    operator fun SharedPreferences.set(key: String, value: Any){
        when(value){
            is Editable -> {
                if (value.toString() != ""){
                    edit { it.putString(key, value.toString()) }
                }
            }
            is String -> {
                if (value != ""){
                    edit { it.putString(key, value) }
                }
            }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Unsupported Operation")
        }
    }

    // an extension function to retrieve the values form the SharedPreferences
    inline operator fun <reified T> SharedPreferences.get(key: String, defaultValue: T? = null): T?{
        return when(T::class){
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Unsupported Operation")
        }
    }

    //////

    fun updateLanguage(language: String){
        val editor = customPrefs.edit()
        editor.putString(LANGUAGE, language)
        editor.apply()
    }

    fun getAppLanguage(): Locale {
        val lang = customPrefs.getString(LANGUAGE, "en")
        return Locale.forLanguageTag(lang!!)
    }

    // Function to save auth token
    fun saveAuthToken(token: String) {
        val editor = customPrefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun saveUserId(userId: Int) {
        val editor = customPrefs.edit()
        editor.putInt(USER_ID, userId)
        editor.apply()
    }

    // Function to fetch auth token
    fun fetchAuthToken(): String? {
        return customPrefs.getString(USER_TOKEN, null)
    }

    fun fetchUserId(): Int {
        return customPrefs.getInt(USER_ID, 0)
    }


}