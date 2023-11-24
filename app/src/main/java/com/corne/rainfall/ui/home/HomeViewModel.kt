package com.corne.rainfall.ui.home

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.di.LocalRainfallRepository
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rainfallPreference: IRainfallPreference,
    @LocalRainfallRepository private val rain: IRainRepository,
) : BaseStateViewModel<IHomeState>() {
    private val stateStore = IHomeState.initialState.mutable()
    override val state: StateFlow<IHomeState> = stateStore.asStateFlow()
    private var currentJob: Job? = null

    fun saveDefaultLocation(locationId: UUID) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            rainfallPreference.setDefaultLocation(locationId)
           /* setState {
                defaultLocation = locationId - 1
            }*/

        }
    }

    suspend fun loadUserLocationData() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {

            setState {
                isLoading = true
            }

            val flow1 = rainfallPreference.defaultLocationFlow
            val flow2 = rain.getAllLocations()


            flow1.combine(flow2) { isGraphBar, rainfallResult ->
                Pair(isGraphBar, rainfallResult)
            }.collect {
                when (val locResult = it.second) {
                    is NetworkResult.Success -> setState {
                        isLoading = false
                        allLocationsList = locResult.data
                        defaultLocation = it.first
                    }

                    is NetworkResult.Error -> setState {
                        isLoading = false
                        error = locResult.message
                    }
                }
            }

       /*     flow1.collect { locId ->
                setState {
                    isLoading = false
                    defaultLocation = locId
                }
            }

            flow1.combine(rain.getAllLocations()) { locId, locResult ->
                when (locResult) {
                    is NetworkResult.Success -> setState {
                        isLoading = false
                        allLocationsList = locResult.data
                        defaultLocation = locId
                    }

                    is NetworkResult.Error -> setState {
                        isLoading = false
                        error = locResult.message
                    }
                }
            }*/

            /* rain.getAllLocations().collect { locResult ->
                 when (locResult) {
                     is NetworkResult.Success -> setState {
                         isLoading = false
                         allLocationsList = locResult.data
                     }

                     is NetworkResult.Error -> setState {
                         isLoading = false
                         error = locResult.message
                     }
                 }
             }*/
        }

    }


    private fun setState(update: MutableIHomeState.() -> Unit) = stateStore.update(update)

}