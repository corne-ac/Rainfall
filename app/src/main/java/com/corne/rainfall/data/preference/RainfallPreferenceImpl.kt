package com.corne.rainfall.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

val Context.rainPreferenceDataStore by preferencesDataStore("rain_preference")

class RainfallPreferenceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : IRainfallPreference {

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
            preferences[IS_OFFLINE_MODE_ENABLED] = enable
        }
    }

    override suspend fun setLanguageMode(locale: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_MODE] = locale
        }
    }


    override val defaultLocationFlow: Flow<UUID?> = dataStore.data.catch {
        it.printStackTrace()
        emit(emptyPreferences())
    }.map { preference ->
        return@map if (preference[DEFAULT_LOCATION] == null) null else UUID.fromString(preference[DEFAULT_LOCATION])
    }

    override suspend fun setDefaultLocation(locationId: UUID) {
        dataStore.edit { preferences ->
            preferences[DEFAULT_LOCATION] = locationId.toString()
        }
    }


    override val defaultGraphFlow: Flow<Boolean> = dataStore.data.catch {
        it.printStackTrace()
        emit(emptyPreferences())
    }.map { preference -> preference[DEFAULT_GRAPH_TYPE] ?: false }

    override suspend fun setDefaultGraph(isBar: Boolean) {
        dataStore.edit { preferences ->
            preferences[DEFAULT_GRAPH_TYPE] = isBar
        }
    }

    override val lastUpdatedDateFlow: Flow<Long?> = dataStore.data.catch {
        it.printStackTrace()
        emit(emptyPreferences())
    }.map { preference -> preference[LAST_UPDATED_CLOUD] }

    override suspend fun setLastUpdatedDate(timestamp: Long) {
        dataStore.edit { preferences ->
            preferences[LAST_UPDATED_CLOUD] = timestamp
        }
    }

    override val lastLocalExportDateFlow: Flow<Long?> = dataStore.data.catch {
        it.printStackTrace()
        emit(emptyPreferences())
    }.map { preference -> preference[LAST_LOCAL_EXPORT_DATE] }

    override suspend fun setLastLocalExportDate(timestamp: Long) {
        dataStore.edit { preferences ->
            preferences[LAST_LOCAL_EXPORT_DATE] = timestamp
        }
    }

    companion object {
        val IS_DARK_MODE_ENABLED = booleanPreferencesKey("dark_mode")
        val IS_OFFLINE_MODE_ENABLED = booleanPreferencesKey("offline_mode")
        val LANGUAGE_MODE = stringPreferencesKey("language_mode")
        val DEFAULT_LOCATION = stringPreferencesKey("default_location")
        val DEFAULT_GRAPH_TYPE = booleanPreferencesKey("default_graph_type")
        val LAST_UPDATED_CLOUD = longPreferencesKey("default_last_updated")
        val LAST_LOCAL_EXPORT_DATE = longPreferencesKey("last_local_export_date")
    }
}