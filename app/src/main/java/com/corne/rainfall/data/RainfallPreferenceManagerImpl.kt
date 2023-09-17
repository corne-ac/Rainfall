package com.corne.rainfall.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.rainPreferenceDataStore by preferencesDataStore("rain_preference")

class RainfallPreferenceManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : IRainfallPreferenceManager {

    override val uiModeFlow: Flow<Boolean> = dataStore.data.catch {
        it.printStackTrace()
        emit(emptyPreferences())
    }.map { preference -> preference[IS_DARK_MODE_ENABLED] ?: false }

    override val offlineModeFlow = dataStore.data.catch {
        it.printStackTrace()
        emit(emptyPreferences())
    }.map { preference -> preference[IS_OFFLINE_MODE_ENABLED] ?: false }

    override val languageModeFlow = dataStore.data.catch {
        it.printStackTrace()
        emit(emptyPreferences())
    }.map { preference ->
        preference[LANGUAGE_MODE] ?: "Default"
    }


    override suspend fun setDarkMode(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE_ENABLED] = enable
        }
    }

    override suspend fun setOfflineMode(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE_ENABLED] = enable
        }
    }

    override suspend fun setLanguageMode(locale: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_MODE] = locale
        }
    }

    companion object {
        val IS_DARK_MODE_ENABLED = booleanPreferencesKey("dark_mode")
        val IS_OFFLINE_MODE_ENABLED = booleanPreferencesKey("offline_mode")
        val LANGUAGE_MODE = stringPreferencesKey("language_mode")
    }
}