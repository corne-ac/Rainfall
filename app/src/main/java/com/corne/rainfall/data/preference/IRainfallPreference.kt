package com.corne.rainfall.data.preference

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Singleton

/**
 * Defines a Singleton interface for managing user preferences related to the application's preferences.
 * Implementations of this interface should provide methods to retrieve and update preference modes.
 */

//The below preferences implementation was derived from Android developers
//https://developer.android.com/training/data-storage/shared-preferences

@Singleton
interface IRainfallPreference {
    /**
     * A [Flow] representing the current UI mode preference.
     * Observers can collect this [Flow] to receive updates when the UI mode changes.
     * The [Flow] emits [Boolean] values indicating whether dark mode is enabled (true) or disabled (false).
     */
    val uiModeFlow: Flow<Boolean>

    /**
     * Suspended function to set the application's UI mode preference.
     * This function allows the caller to enable or disable dark mode based on the provided [enable] parameter.
     *
     * @param enable A [Boolean] parameter indicating whether to enable (true) or disable (false) dark mode.
     */
    suspend fun setDarkMode(enable: Boolean)

    /**
     * A [Flow] representing the current offline mode preference.
     * Observers can collect this [Flow] to receive updates when the offline mode changes.
     * The [Flow] emits [Boolean] values indicating whether offline mode is enabled (true) or disabled (false).
     */
    val offlineModeFlow: Flow<Boolean>

    /**
     * Suspended function to set the application's offline mode preference.
     * This function allows the caller to enable or disable offline mode based on the provided [enable] parameter.
     *
     * @param enable A [Boolean] parameter indicating whether to enable (true) or disable (false) offline mode.
     */
    suspend fun setOfflineMode(enable: Boolean)

    /**
     * A [Flow] representing the current language mode preference.
     * Observers can collect this [Flow] to receive updates when the language mode changes.
     * The [Flow] emits [String] value indicating the language mode selected.
     */
    val languageModeFlow: Flow<String>

    /**
     * Suspended function to set the application's language mode preference.
     * This function allows the caller to set the language mode based on the provided [String] parameter.
     *
     * @param locale A [String] parameter indicating the language mode to be set.
     */
    suspend fun setLanguageMode(locale: String)


    /**
     * A [Flow] representing the current default location preference.
     * Observers can collect this [Flow] to receive updates when the default location changes.
     * The [Flow] emits [Int] values indicating the default location.
     */
    val defaultLocationFlow: Flow<UUID?>

    /**
     * Suspended function to set the application's default location preference.
     * This function allows the caller to set the default location based on the provided [locationId] parameter.
     *
     * @param locationId An [Int] parameter indicating the default location to be set.
     */
    suspend fun setDefaultLocation(locationId: UUID)

    /**
     * A [Flow] representing the current default graph preference.
     * Observers can collect this [Flow] to receive updates when the default graph changes.
     * The [Flow] emits [Boolean] values indicating whether the default graph is bar (true) or line (false).
     */
    val defaultGraphFlow: Flow<Boolean>

    /**
     * Suspended function to set the application's default graph preference.
     * This function allows the caller to set the default graph based on the provided [isBar] parameter.
     *
     * @param isBar A [Boolean] parameter indicating whether to set the default graph to bar (true) or line (false).
     */
    suspend fun setDefaultGraph(isBar: Boolean)

    /**
     * A [Flow] representing the last updated date.
     * Observers can collect this [Flow] to receive updates when the last updated date changes.
     * The [Flow] emits [Long] values indicating the timestamp of the last update.
     * The [Flow] can be null if there is no last updated date yet.
     */
    val lastUpdatedDateFlow: Flow<Long?>

    /**
     * Suspended function to set the application's last updated date.
     * This function allows the caller to set the last updated date based on the provided [timestamp] parameter.
     *
     * @param timestamp A [Long] parameter indicating the timestamp of the last update.
     */
    suspend fun setLastUpdatedDate(timestamp: Long)

    /**
     * A [Flow] representing the last local export date.
     * Observers can collect this [Flow] to receive updates when the last local export date changes.
     * The [Flow] emits [Long] values indicating the timestamp of the last local export.
     * The [Flow] can be null if there is no last local export date yet.
     */
    val lastLocalExportDateFlow: Flow<Long?>

    /**
     * Suspended function to set the application's last local export date.
     * This function allows the caller to set the last local export date based on the provided [timestamp] parameter.
     *
     * @param timestamp A [Long] parameter indicating the timestamp of the last local export.
     */
    suspend fun setLastLocalExportDate(timestamp: Long)


}
