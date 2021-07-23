package com.amanuel.evscsystem.data


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class UserPreferences @Inject constructor(
    @ApplicationContext context: Context
) {

    private val appContext = context.applicationContext

    // return the stored auth key
    val authToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    // return the stored fcm_token
    val fcmToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[FCM_TOKEN]
        }


    // saves the auth key
    suspend fun saveAuthToken(authToken: String) {
        appContext.dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    // saves the fcm_token key
    suspend fun saveFCMToken(fcmToken: String) {
        appContext.dataStore.edit { preferences ->
            preferences[FCM_TOKEN] = fcmToken
        }
    }


    suspend fun clear() {
        appContext.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        val KEY_AUTH = stringPreferencesKey("key_auth")
        private val FCM_TOKEN = stringPreferencesKey("fcm_token")
    }

}