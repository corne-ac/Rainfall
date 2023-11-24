package com.corne.rainfall.ui.rainfall.list

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.di.LocalRainfallRepository
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    @LocalRainfallRepository private val rain: IRainRepository,
    private val rainfallPreference: IRainfallPreference,
) : BaseStateViewModel<ListState>() {
    /*
        override val state: StateFlow<ListState> =
            MutableStateFlow(ListState.initialState).asStateFlow()
        private val stateStore = StateStore(ListState.initialState.mutable())
    */
    private val stateStore = ListState.initialState.mutable()
    override val state: StateFlow<ListState> = stateStore.asStateFlow()
    private var currentJob: Job? = null


    fun getRainfallData() {
        // Cancel the previous job before creating a new one.
        currentJob?.cancel()

        // Launch a coroutine in viewModelScope
        currentJob = viewModelScope.launch {
            // Set the state to loading.
            setState { it.isLoading = true }
            // Call the repository to get the rainfall data.
            val uuid = rainfallPreference.defaultLocationFlow.first() ?: return@launch

            rain.getRainfallInLocation(uuid).collect(::processRainfallResult)
        }
    }

    private fun processRainfallResult(lst: NetworkResult<List<RainfallData>>) {
        when (lst) {
            is NetworkResult.Success -> setState {
                it.isLoading = false
                it.items = lst.data
            }

            is NetworkResult.Error -> setState {
                it.isLoading = false
                it.error = lst.message
            }
        }
    }


    private fun setState(update: (MutableListState) -> Unit) = stateStore.update(update)

}