package com.corne.rainfall.ui.rainfall.capture

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.di.LocalRainfallRepository
import com.corne.rainfall.ui.base.form.FormItem
import com.corne.rainfall.ui.base.form.IKey
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.utils.DataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel @Inject constructor(
    @LocalRainfallRepository private val rain: IRainRepository,
// Here we will be injecting the dependencies required by the ViewModel
) : BaseStateViewModel<CaptureState>() {/* private val stateStore = StateStore(CaptureState.initialState.mutable())
     override val state: StateFlow<CaptureState> = stateStore.state*/

    private val stateStore = MutableCaptureState(false, -1, mutableMapOf())
    override val state: StateFlow<CaptureState> = stateStore.asStateFlow()

    private var currentJob: Job? = null

    fun add() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            val value = state.value

            /*    val rainData = RainData(
                    value.date, value.startTime, value.endTime, value.rainMm, value.notes
                )*/

//            rain.addRainData(rainData).onSuccess { onSuccess() }.onError(::onError)

            setState { isLoading = true }

        }

    }

    private fun onError(errorMessage: Int) {
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


    fun setUpForm() {
        state.value.formValues.apply {
            put(CaptureForm.DATE, FormItem(validationTest = DataValidator::dateValidation))
            put(CaptureForm.START_TIME, FormItem())
            put(CaptureForm.END_TIME, FormItem())
            put(CaptureForm.RAIN_MM, FormItem())
        }
    }

    fun isFormValid(): Boolean {
       /* state.value.formValues.forEach {
            it.value.validate()
        }*/
        return state.value.formValues.all { it.value.isValid }
    }

    fun updateState(key: IKey, value: String) {
        setState {
            formValues = formValues.toMutableMap().apply {
                val oldFormItem = get(key)
                val newFormItem = oldFormItem!!.setValue(value)
                put(key, newFormItem)
            }
        }
    }

    private fun setState(update: MutableCaptureState.() -> Unit) = stateStore.update(update)

}