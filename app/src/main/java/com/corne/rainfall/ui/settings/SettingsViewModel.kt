package com.corne.rainfall.ui.settings

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.data.model.PrefModel
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.data.storage.RainfallRemoteRepo
import com.corne.rainfall.data.task.IRainTaskManager
import com.corne.rainfall.db.RainfallDatabase
import com.corne.rainfall.di.LocalRainfallRepository
import com.corne.rainfall.di.RemoteRainfallRepository
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.utils.NetworkResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
    @RemoteRainfallRepository private val remoteRepo: IRainRepository,
    @LocalRainfallRepository private val localRepo: IRainRepository,
    private val rainfallPreference: IRainfallPreference,
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


    fun uploadData() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            if (remoteRepo is RainfallRemoteRepo) {
                remoteRepo.clear()
                val darkMode = rainfallPreference.uiModeFlow.first()
                val language = rainfallPreference.languageModeFlow.first()
                val offline = rainfallPreference.offlineModeFlow.first()
                val location = rainfallPreference.defaultLocationFlow.first()
                val graphType = rainfallPreference.defaultGraphFlow.first()
                remoteRepo.uploadPreferences(
                    PrefModel(darkMode, language, offline, location, graphType, Date().time)
                )
            }

            val firstTask = localRepo.getAllRainfallData()
            val second = localRepo.getAllLocations()

            firstTask.combine(second) { f, s ->
                Pair(f, s)
            }.collect {
                if (it.first is NetworkResult.Success && it.second is NetworkResult.Success) {
                    (it.first as NetworkResult.Success<List<RainfallData>>).data.forEach { rain ->
                        remoteRepo.addRainData(
                            rain,
                            rain.locId
                        )
                    }
                    (it.second as NetworkResult.Success<List<LocationModel>>).data.forEach { loc ->
                        remoteRepo.addLocation(loc)
                    }
                }

            }


            firstTask.collect { rain ->
                if (rain is NetworkResult.Success) rain.data.forEach {
                    remoteRepo.addRainData(
                        it, it.locId
                    )
                }
            }

            second.collect {
                if (it is NetworkResult.Success) it.data.forEach { loc ->
                    remoteRepo.addLocation(loc)
                }
            }


        }
    }

    fun downloadData() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            val lastUpdated = rainfallPreference.lastUpdatedDateFlow.first()
            if (lastUpdated == null) {
                // We have never synced before
                // Here we want to get all the data from the remote source and save it to the local source

                if (remoteRepo is RainfallRemoteRepo) {
                    remoteRepo.downloadPreferences().collect {
                        if (it is NetworkResult.Success) {
                            rainfallPreference.setDarkMode(it.data.darkMode)
                            rainfallPreference.setLanguageMode(it.data.language)
                            rainfallPreference.setOfflineMode(it.data.offline)
                            rainfallPreference.setDefaultGraph(it.data.graphType)
                            rainfallPreference.setLastUpdatedDate(it.data.lastUpdated!!)
                            if (it.data.location != null) rainfallPreference.setDefaultLocation(it.data.location)
                        }
                    }
                }
                // Add all the location to the local source
                remoteRepo.getAllLocations().collect { loc ->
                    if (loc is NetworkResult.Success) loc.data.forEach { localRepo.addLocation(it) }
                }
                // Add all the rain data to the local sources
                remoteRepo.getAllRainfallData().collect { rain ->
                    if (rain is NetworkResult.Success) rain.data.forEach {
                        localRepo.addRainData(
                            it,
                            it.locId
                        )
                    }
                }
            }
        }
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