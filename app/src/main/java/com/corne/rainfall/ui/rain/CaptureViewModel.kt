package com.corne.rainfall.ui.rain

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.corne.rainfall.data.IRainRepository
import com.corne.rainfall.data.RainfallDatabase
import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.data.model.RainfallEntryModel
import com.corne.rainfall.data.model.RainValidator
import com.corne.rainfall.ui.base.BaseViewModel
import com.corne.rainfall.ui.base.StateStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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


    fun add(con:Context) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            setState { it.isLoading = true }


            val db = Room.databaseBuilder(
                con,
                RainfallDatabase::class.java, "database-name"
            ).build()

            db.rainfallDAO().insert(LocationModel(1, "Pretoria"),LocationModel(11, "Pretori`a"))
            var loc = db.rainfallDAO().getAllLocations()


//            println(loc.[0].name)
//            rain.addRainData(RainfallEntryModel(0.0, "Test")).onSuccess { onSuccess() }.onError(::onError)
        }
    }
    fun get(con: Context): Flow<List<LocationModel>> {
        val db = Room.databaseBuilder(
            con,
            RainfallDatabase::class.java, "database-name"
        ).build()
        var loc = db.rainfallDAO().getAllLocations()
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            loc.collect{ flow ->
                flow.forEach{
                    println(it)
                }
            }
            print("")
//            loc.asLiveData().observe(this);
        }
        return loc;
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
    fun setDate(date: String) {
        setState {
            it.date = date
        }
        // TODO: call validation method.
        validateRain()
    }

    fun setStartTime(startTime: String) {
        setState {
            it.startTime = startTime
        }
        // TODO: call validation method.
        validateRain()
    }

    fun setEndTime(endTime: String) {
        setState {
            it.endTime = endTime
        }
        // TODO: call validation method.
        validateRain()
    }

    fun setRainMm(rainMm: String) {
        setState {
            it.rainMm = rainMm
        }
        // TODO: call validation method.
        validateRain()
    }

    private fun validateRain() {


        val isValid = RainValidator.isValidRain(currentState.date, currentState.startTime, currentState.endTime, currentState.rainMm)
        setState {
            it.error = isValid
        }
    }


}