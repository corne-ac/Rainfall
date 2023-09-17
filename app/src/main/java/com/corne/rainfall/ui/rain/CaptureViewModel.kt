package com.corne.rainfall.ui.rain

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.IRainRepository
import com.corne.rainfall.data.model.RainData
import com.corne.rainfall.ui.base.BaseViewModel
import com.corne.rainfall.ui.base.StateStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel @Inject constructor(
     private val rain: IRainRepository,
// Here we will be injecting the dependencies required by the ViewModel
) : BaseViewModel<CaptureState>() {
    override val state: StateFlow<CaptureState> =
        MutableStateFlow(CaptureState.initialState).asStateFlow()
    private val stateStore = StateStore(CaptureState.initialState.mutable())

    private var currentJob: Job? = null


    fun add() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            setState { isLoading = true }
            rain.addRainData(RainData(0.0, "Test")).onSuccess { onSuccess() }.onError(::onError)
        }
    }

    private fun onError(errorMessage: String) {
        // TODO: show the error message to the user.
        setState {
            isLoading = false
            error = errorMessage
        }
    }

    private fun onSuccess() {
        setState {
            isLoading = false
            error = null
        }
    }


    private fun setState(update: MutableCaptureState.() -> Unit) = stateStore.setState(update)

}