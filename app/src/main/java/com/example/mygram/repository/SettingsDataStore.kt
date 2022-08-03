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

}