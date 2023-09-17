package com.corne.rainfall.ui.settings

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.IRainfallPreferenceManager
import com.corne.rainfall.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
// Here we will be injecting the dependencies required by the ViewModel
    private val preferenceManager: IRainfallPreferenceManager,
) : BaseViewModel<SettingsState>() {
    override val state: StateFlow<SettingsState> = MutableStateFlow(SettingsState()).asStateFlow()

    suspend fun isDarkModeEnabled() = preferenceManager.uiModeFlow.first()
    fun setDarkMode(enable: Boolean) {
        viewModelScope.launch { preferenceManager.setDarkMode(enable) }
    }

    suspend fun getLanguage() = preferenceManager.languageModeFlow.first()
    fun setLanguage(language: String) {
        viewModelScope.launch { preferenceManager.setLanguageMode(language) }
    }


}