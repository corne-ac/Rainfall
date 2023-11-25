package com.corne.rainfall.data.api

import com.corne.rainfall.data.model.AlertModel
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface IWeatherAlertApiProvider {
    fun getAlerts(
        apiKey: String,
        location: String,
        days: String
    ): Flow<NetworkResult<List<NetworkResult<AlertModel>>>>
}
