package ar.edu.unlam.mobile.scaffolding.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

object DataStoreManager {
    private lateinit var dataStore: DataStore<Preferences>

    fun initialize(context: Context) {
        dataStore = context.dataStore
    }

    suspend fun <T> writeToDataStore(
        key: Preferences.Key<T>,
        value: T,
    ) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun <T> readFromDataStore(
        key: Preferences.Key<T>,
        defaultValue: T,
    ): Flow<T> =
        dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }

    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    object Keys {
        val USERNAME_KEY = stringPreferencesKey("username")
        val EMAIL_KEY = stringPreferencesKey("email")
        val IS_ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        val IS_DARK_MODE_ENABLED = booleanPreferencesKey("dark_mode")
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notifications_enabled")
        val LAST_LOGIN_TIMESTAMP = longPreferencesKey("last_login_timestamp")
        val SHOW_TOOLTIP = booleanPreferencesKey("show_tooltip")
        val HAS_REQUESTED_LOCATION_PERMISSIONS = booleanPreferencesKey("has_requested_location_permissions")
        // Add other keys as needed
    }
}
