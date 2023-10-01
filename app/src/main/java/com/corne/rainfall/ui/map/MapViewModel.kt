package com.corne.rainfall.ui.map

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.api.IFireApiProvider
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val fireApi: IFireApiProvider,
    private val rainfallPreference: IRainfallPreference,
) : BaseStateViewModel<IMapState>() {
    private val stateStore = IMapState.initialState.mutable()
    override val state: StateFlow<IMapState> = stateStore.asStateFlow()
    private var currentJob: Job? = null

    fun getDarkModePreference() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            rainfallPreference.uiModeFlow.collect {
                setState {
                    isDarkMode = it
                }
            }
        }
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
}