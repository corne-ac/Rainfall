package com.corne.rainfall.ui.rainfall.capture

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.di.LocalRainfallRepository
import com.corne.rainfall.ui.base.form.FormItem
import com.corne.rainfall.ui.base.form.IKey
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.utils.DataValidator
import com.corne.rainfall.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel @Inject constructor(
    @LocalRainfallRepository private val rain: IRainRepository,
    private val rainfallPreference: IRainfallPreference,
) : BaseStateViewModel<CaptureState>() {
    private val stateStore = CaptureState.initialState.mutable()
    override val state: StateFlow<CaptureState> = stateStore.asStateFlow()
    private var currentJob: Job? = null
    private var onSuccessCallback: (() -> Unit)? = null
    private var onFailCallback: (() -> Unit)? = null

    fun add() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            val stateValue = state.value
            setState { isLoading = true }

            val date = SimpleDateFormat(
                "dd/MM/yyyy", Locale.ENGLISH
            ).parse(stateValue.formValues[CaptureForm.DATE]!!.getValue()!!)!!

            //TODO: add location thing to top of page

            val r = RainfallData(
                rainfallPreference.defaultLocationFlow.first()!!,
                date,
                stateValue.formValues[CaptureForm.START_TIME]!!.getValue()!!,
                stateValue.formValues[CaptureForm.END_TIME]!!.getValue()!!,
                stateValue.formValues[CaptureForm.RAIN_MM]!!.getValue()!!.toDouble(),
                stateValue.formValues[CaptureForm.NOTES]!!.getValue()!!
            )

            val locId: UUID = state.value.defaultLocation!!

            rain.addRainData(r, locId).onSuccess {
                onSuccess()
            }.onError(::onError)


        }

    }

    private fun onError(errorMessage: Int) {
        setState {
            isLoading = false
            error = errorMessage
        }
        onFailCallback?.invoke()
    }

    private fun onSuccess() {
        setState {
            isLoading = false
            error = null
        }
        onSuccessCallback?.invoke()
    }

    fun setOnSuccessCallback(callback: () -> Unit) {
        onSuccessCallback = callback
    }

    fun setOnFailCallback(callback: () -> Unit) {
        onFailCallback = callback
    }

    fun setUpForm() {
        // @formatter:off
        state.value.formValues.apply {
            put(CaptureForm.DATE,       FormItem(validationTest = DataValidator::dateValidation))
            put(CaptureForm.START_TIME, FormItem(validationTest = DataValidator::startTimeValidation))
            put(CaptureForm.END_TIME,   FormItem(validationTest = DataValidator::endTimeValidation))
            put(CaptureForm.RAIN_MM,    FormItem())
            put(CaptureForm.NOTES,    FormItem())
        }
        // @formatter:on
    }

    fun isFormValid(): Boolean {
        return state.value.formValues.filter { it.key != CaptureForm.NOTES || it.value.isValid }
            .all { it.value.isValid }
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
        }

    }

}