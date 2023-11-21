package com.corne.rainfall.ui.settings

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Settings screen.
 *
 * This ViewModel is responsible for managing the state of the Settings screen and interacting with the RainfallPreference.
 * It extends the BaseStateViewModel with a state of type SettingsState.
 *
 * @property preferenceManager The preference manager used to get and set user preferences, accessed via dependency injection.
 * @property state The state of the Settings screen, exposed as a StateFlow.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferenceManager: IRainfallPreference,
) : BaseStateViewModel<SettingsState>() {
    override val state: StateFlow<SettingsState> = MutableStateFlow(SettingsState()).asStateFlow()

    /**
     * Checks if dark mode is enabled.
     *
     * This function uses the preference manager to get the current value of the dark mode setting.
     *
     * @return A boolean indicating whether dark mode is enabled.
     */
    suspend fun isDarkModeEnabled() = preferenceManager.uiModeFlow.first()

    /**
     * Sets the dark mode setting.
     *
     * This function uses the preference manager to set the value of the dark mode setting.
     *
     * @param enable A boolean indicating whether to enable dark mode.
     */
    fun setDarkMode(enable: Boolean) {
        viewModelScope.launch { preferenceManager.setDarkMode(enable) }
    }

    /**
     * Gets the current language setting.
     *
     * This function uses the preference manager to get the current value of the language setting.
     *
     * @return A string representing the current language setting.
     */
    suspend fun getLanguage() = preferenceManager.languageModeFlow.first()

    /**
     * Sets the language setting.
     *
     * This function uses the preference manager to set the value of the language setting.
     *
     * @param language A string representing the language to set.
     */
    fun setLanguage(language: String) {
        viewModelScope.launch { preferenceManager.setLanguageMode(language) }
    }

}