package com.example.mygram.repository


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SETTINGS_NAME = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_NAME)

class SettingsDataStore(context: Context) {
    private val TEXT_SIZE = intPreferencesKey("text_size")
    suspend fun saveTextSize(textSize: Int, context: Context){
        context.dataStore.edit {
            it[TEXT_SIZE] = textSize
        }
    }
    val textSizeFlow: Flow<Int> = context.dataStore.data.map {
        it[TEXT_SIZE] ?: 0
    }

    private val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
    suspend fun saveNotificationEnabled(enable: Boolean, context: Context){
        context.dataStore.edit {
            it[NOTIFICATION_ENABLED] = enable
        }
    }
    val notificationEnabled: Flow<Boolean> = context.dataStore.data.map {
        it[NOTIFICATION_ENABLED] ?: true
    }
}