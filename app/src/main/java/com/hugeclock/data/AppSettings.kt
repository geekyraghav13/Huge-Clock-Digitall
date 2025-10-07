package com.hugeclock.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey // NEW IMPORT
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AppSettings(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val IS_24_HOUR_FORMAT = booleanPreferencesKey("is_24_hour_format")
        // NEW: Keys for storing colors as strings
        val CLOCK_COLOR = stringPreferencesKey("clock_color")
        val BACKGROUND_COLOR = stringPreferencesKey("background_color")
    }

    // --- 24-Hour Format Setting ---
    val is24HourFormat: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_24_HOUR_FORMAT] ?: false
    }
    suspend fun set24HourFormat(is24Hour: Boolean) {
        dataStore.edit { settings ->
            settings[IS_24_HOUR_FORMAT] = is24Hour
        }
    }

    // --- Clock Color Setting ---
    val clockColor: Flow<String> = dataStore.data.map { preferences ->
        preferences[CLOCK_COLOR] ?: "#FFFFFFFF" // Default to White
    }
    suspend fun setClockColor(color: String) {
        dataStore.edit { settings ->
            settings[CLOCK_COLOR] = color
        }
    }

    // --- Background Color Setting ---
    val backgroundColor: Flow<String> = dataStore.data.map { preferences ->
        preferences[BACKGROUND_COLOR] ?: "#FF000000" // Default to Black
    }
    suspend fun setBackgroundColor(color: String) {
        dataStore.edit { settings ->
            settings[BACKGROUND_COLOR] = color
        }
    }
}