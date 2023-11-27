package com.corne.rainfall.ui.notification.list

import androidx.lifecycle.viewModelScope
import com.corne.rainfall.R
import com.corne.rainfall.data.api.IWeatherAlertApiProvider
import com.corne.rainfall.data.model.AlertModel
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.corne.rainfall.BuildConfig

@HiltViewModel
class NotificationListViewModel @Inject constructor(private val alertApi: IWeatherAlertApiProvider) :
    BaseStateViewModel<NotificationsState>() {
    private val stateStore = NotificationsState.initialState.mutable()
    override val state: StateFlow<NotificationsState> = stateStore.asStateFlow()
    private var currentJob: Job? = null


    fun getAllAlerts() {
        val iataCodes = listOf("CPT", "GRJ", "DUR", "JNB")

        viewModelScope.launch {
            setState { isLoading = true }

            val results = mutableListOf<List<AlertModel>>()

            for (iataCode in iataCodes) {
                val alertsData = alertApi.getAlerts(BuildConfig.WEATHER_ALERT_API_KEY, "iata:$iataCode", "1")

                alertsData.collect { resp ->
                    resp.onSuccess { networkResultList ->
                        val itemList = networkResultList.alertsMain?.alert
                        if (itemList != null) {
                            results.add(itemList)
                        }
                    }.onError { errorMsg ->
                        setState {
                            isLoading = false
                            error = errorMsg
                        }
                        return@collect
                    }
                }
            }
            val combinedList = results.flatten()
            setState {
                isLoading = false
                items = combinedList
            }
        }
    }

    fun getAlerts(iataCode: String): Deferred<List<AlertModel>> {
        currentJob?.cancel()

        return viewModelScope.async {
            setState { isLoading = true }
            val alertsData = alertApi.getAlerts("9f9a9d1b7bed4c0fa3a120250232511", "iata:${iataCode}", "1")

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
            stateStore.items
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

     fun setState(update: MutableNotificationsState.() -> Unit) = stateStore.update(update)
}
