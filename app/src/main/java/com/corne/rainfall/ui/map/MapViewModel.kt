package com.corne.rainfall.ui.map

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.api.WeatherApiService
import com.corne.rainfall.data.api.IFireApiProvider
import com.corne.rainfall.data.conection.ConnectedState
import com.corne.rainfall.data.conection.IConnectedObserver
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val fireApi: IFireApiProvider,
    private val connectedObserver: IConnectedObserver,
    private val rainfallPreference: IRainfallPreference,
    private val weatherApiService: WeatherApiService,
) : BaseStateViewModel<IMapState>() {
    private val stateStore = IMapState.initialState.mutable()
    override val state: StateFlow<IMapState> = stateStore.asStateFlow()
    private var currentJob: Job? = null

    fun updateForMap() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {

            setState {
                isLoading = true
            }

            combine(
                rainfallPreference.offlineModeFlow,
                connectedObserver.connectionState,
                rainfallPreference.uiModeFlow
            ) { offline, connected, darkMode ->
                Triple(offline, connected, darkMode)
            }.collect { (offline, connected, darkMode) ->
                setState {
                    isOfflinePresence = offline
                    isConnected = connected == ConnectedState.Available
                    isDarkMode = darkMode
                    isLoading = false
                }
            }
        }
    }

    fun getWeatherApiService(): WeatherApiService {
        return weatherApiService
    }

    fun getFireData(
        apiKey: String,
        type: String,
        location: String,
        days: Int,
        date: String,
        map: GoogleMap,
    ) {
        // Cancel the previous job before creating a new one.
        currentJob?.cancel()

        // Launch a coroutine in viewModelScope
        currentJob = viewModelScope.launch {
            setState {
                isLoading = true
            }
            // Get the data from retrofit
            val fireData = fireApi.getFireData(apiKey, type, location, days, date)
            fireData.collect { resp ->
                resp.onSuccess { itemList ->
                    setState {
                        isLoading = false
                        items = itemList
                        googleMap = map
                    }
                }.onError { errorMsg ->
                    setState {
                        isLoading = false
                        error = errorMsg
                        googleMap = map
                    }
                }
            }
        }
    }


    private fun setState(update: MutableIMapState.() -> Unit) = stateStore.update(update)
    fun setMap(googleMap: GoogleMap) {
        setState {
            this.googleMap = googleMap
        }
    }

}