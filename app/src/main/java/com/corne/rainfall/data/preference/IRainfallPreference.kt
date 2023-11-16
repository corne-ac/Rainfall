package com.corne.rainfall.data.preference

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

/**
 * Defines a Singleton interface for managing user preferences related to the application's preferences.
 * Implementations of this interface should provide methods to retrieve and update preference modes.
 */
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
    val defaultLocationFlow: Flow<Int>

    /**
     * Suspended function to set the application's default location preference.
     * This function allows the caller to set the default location based on the provided [locationId] parameter.
     *
     * @param locationId An [Int] parameter indicating the default location to be set.
     */
    suspend fun setDefaultLocation(locationId: Int)

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


}
