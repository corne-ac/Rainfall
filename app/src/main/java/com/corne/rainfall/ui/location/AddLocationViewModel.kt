package com.corne.rainfall.ui.location

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.di.LocalRainfallRepository
import com.corne.rainfall.ui.base.form.FormItem
import com.corne.rainfall.ui.base.form.IKey
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    @LocalRainfallRepository private val rain: IRainRepository,
) : BaseStateViewModel<AddLocationState>() {
    private val stateStore = AddLocationState.initialState.mutable()
    override val state: StateFlow<AddLocationState> = stateStore.asStateFlow()

    private var currentJob: Job? = null

    fun add() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            val stateValue = state.value
            setState { isLoading = true }
            stateValue.formValues[ILocationForm.NAME]!!.getValue()!!

            rain.addLocation(
                LocationModel(
                    UUID.randomUUID(),
                    stateValue.formValues[ILocationForm.NAME]!!.getValue()!!
                )
            ).let {
                when (it) {
                    is NetworkResult.Success -> onSuccess()
                    is NetworkResult.Error -> onError(it.message)
                }
            }


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
            put(ILocationForm.NAME, FormItem())
        }
    }

    fun isFormValid(): Boolean {
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

    private fun setState(update: MutableAddLocationState.() -> Unit) = stateStore.update(update)

}