package com.corne.rainfall.ui.notification.list

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.R
import com.corne.rainfall.data.api.IWeatherAlertApiProvider
import com.corne.rainfall.data.model.AlertModel
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationListViewModel @Inject constructor(private val alertApi: IWeatherAlertApiProvider) :
    BaseStateViewModel<NotificationsState>() {
    private val stateStore = NotificationsState.initialState.mutable()
    override val state: StateFlow<NotificationsState> = stateStore.asStateFlow()
    private var currentJob: Job? = null

    fun getAlerts() {
        currentJob?.cancel()

        currentJob = viewModelScope.launch {
            setState { isLoading = true }

            val alertsData = alertApi.getAlerts("9f9a9d1b7bed4c0fa3a120250232511", "iata:JNB", "1")

            alertsData.collect { resp ->
                resp.onSuccess { networkResultList ->
                    val itemList = networkResultList.alertsMain?.alert
                    if (itemList == null) {
                        setState {
                            isLoading = false
                            error = R.string.NoAllertsFound
                        }
                        return@collect
                    } else
                        setState {
                            isLoading = false
                            items = itemList
                        }

                }.onError { errorMsg ->
                    setState {
                        isLoading = false
                        error = errorMsg
                    }
                }
            }
        }
    }


    private fun processAlertsResult(result: NetworkResult<List<AlertModel>>) {
        when (result) {
            is NetworkResult.Success -> setState {
                isLoading = false
                items = result.data
            }

            is NetworkResult.Error -> setState {
                isLoading = false
                error = result.message
            }
        }
    }

    private fun setState(update: MutableNotificationsState.() -> Unit) = stateStore.update(update)
}
