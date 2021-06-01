package com.amanuel.evscsystem.data


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class UserPreferences(private val context: Context) {


    // return the stored auth key
    val authToken: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }


    // saves the auth key
    suspend fun saveAuthToken(authToken: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }


    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val KEY_AUTH = stringPreferencesKey("key_auth")
    }

}