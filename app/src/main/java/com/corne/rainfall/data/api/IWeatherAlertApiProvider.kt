package com.corne.rainfall.data.api

import com.corne.rainfall.data.model.AlertResponseModel
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface IWeatherAlertApiProvider {
    fun getAlerts(
        apiKey: String,
        location: String,
        days: String
    ): Flow<NetworkResult<AlertResponseModel.AlertsMain>>
}
