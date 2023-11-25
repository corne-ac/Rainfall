package com.corne.rainfall.ui.notification.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.corne.rainfall.data.api.IFireApiProvider
import com.corne.rainfall.data.api.IWeatherAlertApiProvider
import com.corne.rainfall.data.model.AlertModel
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.utils.NetworkResult
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationListViewModel @Inject constructor(private val alertApi: IWeatherAlertApiProvider) : BaseStateViewModel<NotificationsState>() {
    private val stateStore = NotificationsState.initialState.mutable()
    override val state: StateFlow<NotificationsState> = stateStore.asStateFlow()
    private var currentJob: Job? = null

    fun getAlerts() {
        currentJob?.cancel()

        currentJob = viewModelScope.launch {
            setState { stateStore.isLoading = true }

            val alertsData = alertApi.getAlerts("9f9a9d1b7bed4c0fa3a120250232511", "iata:JNB", "1")

            alertsData.collect { resp ->
                Log.d("NotificationListViewModel", "getAlerts: $resp")

                resp.onSuccess { networkResultList ->
                    val itemList = networkResultList.mapNotNull {
                        when (it) {
                            is NetworkResult.Success -> it.data
                            is NetworkResult.Error -> null
                        }
                    }

                    setState {
                        stateStore.isLoading = false
                        stateStore.items = itemList
                    }

                }.onError { errorMsg ->
                    setState {
                        stateStore.isLoading = false
                        stateStore.error = errorMsg
                    }
                }
            }
        }
    }


    private fun processAlertsResult(result: NetworkResult<List<AlertModel>>) {
        when (result) {
            is NetworkResult.Success -> setState {
                it.isLoading = false
                it.items = result.data
            }

            is NetworkResult.Error -> setState {
                it.isLoading = false
                it.error = result.message
            }
        }
    }

    private fun setState(update: (MutableNotificationsState) -> Unit) = stateStore.update(update)
}
