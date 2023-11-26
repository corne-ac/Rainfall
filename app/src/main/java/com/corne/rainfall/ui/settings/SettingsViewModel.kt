package com.corne.rainfall.ui.settings

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.data.task.IRainTaskManager
import com.corne.rainfall.data.worker.RainSyncWorker
import com.corne.rainfall.db.RainfallDatabase
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date
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
    private val rainTaskManager: IRainTaskManager,
) : BaseStateViewModel<ISettingsState>() {
    private val stateStore = ISettingsState.initialState.mutable()
    override val state: StateFlow<ISettingsState> = stateStore.asStateFlow()

    private var currentJob: Job? = null


    /**
     * Checks if dark mode is enabled.
     *
     * This function uses the preference manager to get the current value of the dark mode setting.
     *
     * @return A boolean indicating whether dark mode is enabled.
     */
    suspend fun isDarkModeEnabled() = preferenceManager.uiModeFlow.first()

    fun isLoggedIn() = FirebaseAuth.getInstance().currentUser != null

    fun checkLoggedIn() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            setState { loggedIn = FirebaseAuth.getInstance().currentUser != null }
        }
    }

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
     * Checks if offline mode is enabled.
     *
     * This function uses the preference manager to get the current value of the offline mode setting.
     *
     * @return A boolean indicating whether offline mode is enabled.
     */
    suspend fun isOfflineModeEnabled() = preferenceManager.offlineModeFlow.first()

    /**
     * Sets the offline mode setting.
     *
     * This function uses the preference manager to set the value of the offline mode setting.
     *
     * @param enable A boolean indicating whether to enable offline mode.
     */
    fun setOfflineMode(enable: Boolean) {
        viewModelScope.launch { preferenceManager.setOfflineMode(enable) }
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

    fun exportDataCloud() {
//        rainTaskManager.exportData()
//        currentJob?.cancel()
//        currentJob = viewModelScope.launch {
//            rainSyncWorker.syncRainData()
//        }
    }

    fun importData() {
        rainTaskManager.importData()
    }

    fun exportDataLocal(ctx: Context) {
        viewModelScope.launch { preferenceManager.setLastLocalExportDate(Date().time) }
        RainfallDatabase.INSTANCE?.backupDatabase(ctx)
    }

    fun importDataLocal(ctx: Context) {
        RainfallDatabase.INSTANCE?.restoreDatabase(ctx)
    }


    private fun setState(update: MutableISettingsState.() -> Unit) = stateStore.update(update)


}