package com.corne.rainfall.data.api

import com.corne.rainfall.data.model.AlertResponseModel
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

/**
 * This is a singleton interface for the Weather Alert API Provider.
 * It provides a method to get alerts from the API.
 */
@Singleton
interface IWeatherAlertApiProvider {
    /**
     * This method fetches alerts from the API.
     *
     * @param apiKey The API key used for authentication.
     * @param location The location for which alerts are to be fetched.
     * @param days The number of days for which alerts are to be fetched.
     * @return A Flow of NetworkResult containing AlertResponseModel.AlertsMain.
     */
    fun getAlerts(
        apiKey: String,
        location: String,
        days: String
    ): Flow<NetworkResult<AlertResponseModel.AlertsMain>>
}