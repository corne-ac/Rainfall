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
            setState { it.isLoading = true }
            rain.addRainData(RainData(0.0, "Test")).onSuccess { onSuccess() }.onError(::onError)
        }
    }

    private fun onError(errorMessage: Int) {
        setState {
            it.isLoading = false
            it.error = errorMessage
        }
    }

    private fun onSuccess() {
        setState {
            it.isLoading = false
            it.error = null
        }
    }


    private fun setState(update: (MutableCaptureState) -> Unit) = stateStore.setState(update)
    fun setDate(toString: String) {
        setState {
            it.date = toString
        }
        // TODO: call validation method.
    }

    fun setStartTime(startTime: String) {
        setState {
            it.startTime = startTime
        }
        // TODO: call validation method.
    }

    fun setEndTime(endTime: String) {
        setState {
            it.endTime = endTime
        }
        // TODO: call validation method.
    }

    fun setRainMm(rainMm: String) {
        setState {
            it.rainMm = rainMm
        }
        // TODO: call validation method.
    }


}