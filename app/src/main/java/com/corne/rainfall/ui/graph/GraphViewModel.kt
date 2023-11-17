package com.corne.rainfall.ui.graph

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
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val rainfallPreference: IRainfallPreference,
    @LocalRainfallRepository private val rain: IRainRepository,
) : BaseStateViewModel<IGraphState>() {
    private val stateStore = IGraphState.initialState.mutable()
    override val state: StateFlow<IGraphState> = stateStore.asStateFlow()
    private var currentJob: Job? = null

    fun updateGraphType() {
        setState { isGraphBar = !isGraphBar }
    }

    fun getDataForGraph() {
        // Cancel the previous job before creating a new one.
        currentJob?.cancel()
        // Launch a coroutine in viewModelScope
        currentJob = viewModelScope.launch {
            setState { isLoading = true }

            rainfallPreference.defaultLocationFlow.collect { locId ->

                // TODO: What to do if the default location is not set?
                // For now we will just use a default
                val locIdNew = if (locId == -1) {
                    1
                } else {
                    locId
                }
                val flow1 = rainfallPreference.defaultGraphFlow
                val flow2 = rain.getRainfallInLocation(locIdNew)
                flow1.combine(flow2) { isGraphBar, rainfallResult ->
                    Pair(isGraphBar, rainfallResult)
                }.collect {
                    when (val rainfallResult = it.second) {
                        is NetworkResult.Success -> setState {
                            isLoading = false
                            rainDataList = rainfallResult.data
                            isGraphBar = it.first
                        }

                        is NetworkResult.Error -> setState {
                            isLoading = false
                            error = rainfallResult.message
                        }
                    }
                }
            }
        }

    }

    private fun setState(update: MutableIGraphState.() -> Unit) = stateStore.update(update)
}