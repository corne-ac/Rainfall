package com.corne.rainfall.ui.home

import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.di.LocalRainfallRepository
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rainfallPreference: IRainfallPreference,
    @LocalRainfallRepository private val rain: IRainRepository,
) : BaseStateViewModel<IHomeState>() {
    private val stateStore = IHomeState.initialState.mutable()
    override val state: StateFlow<IHomeState> = stateStore.asStateFlow()
    private var currentJob: Job? = null

    suspend fun loadUserLocationData() {
        setState {
            isLoading = true
        }


        rainfallPreference.defaultLocationFlow.collect { locId ->
            setState {
                isLoading = false
                defaultLocation = locId
            }
        }

        rain.getAllLocations().collect { locResult ->
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
        }

    }


    private fun setState(update: MutableIHomeState.() -> Unit) = stateStore.update(update)

}