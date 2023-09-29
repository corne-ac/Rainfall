package com.corne.rainfall.ui.rainfall.list

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.di.LocalRainfallRepository
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.ui.base.state.StateStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    @LocalRainfallRepository private val rain: IRainRepository,
) : BaseStateViewModel<ListState>() {
    override val state: StateFlow<ListState> =
        MutableStateFlow(ListState.initialState).asStateFlow()
    private val stateStore = StateStore(ListState.initialState.mutable())

    private var currentJob: Job? = null


    fun getRainfallData() {
        // Cancel the previous job before creating a new one.
        currentJob?.cancel()

        // Launch a coroutine in viewModelScope
        currentJob = viewModelScope.launch {
            // Set the state to loading.
            setState { it.isLoading = true }

            // Call the repository to get the rainfall data.
            rain.getRainfallInLocation(1)
            // TODO: what to do with the result?
        }
    }

    private fun setState(update: (MutableListState) -> Unit) = stateStore.setState(update)

}