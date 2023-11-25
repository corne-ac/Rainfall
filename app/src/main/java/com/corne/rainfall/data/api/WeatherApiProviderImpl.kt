package com.corne.rainfall.data.api

import android.util.Log
import com.corne.rainfall.api.WeatherAlertApiService
import com.corne.rainfall.data.model.AlertModel
import com.corne.rainfall.data.model.AlertResponseModel
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject

class WeatherAlertApiProviderImpl @Inject constructor(
    private val weatherAlertApiService: WeatherAlertApiService,
) : IWeatherAlertApiProvider {

    override fun getAlerts(
        apiKey: String,
        location: String,
        days: String,
    ): Flow<NetworkResult<AlertResponseModel.AlertsMain>> = flow {

        val alertData =
            weatherAlertApiService.getWeatherAlerts(apiKey, "iata:JNB", days, "no", "yes")

        if (!alertData.isSuccessful) {
            Log.d("Error here ", "$alertData")
            emit(NetworkResult.Error(alertData.code()))
            return@flow
        }
        if (alertData.body() == null) {
            Log.d("Error here ", "$alertData")
            emit(NetworkResult.Error(-1))
        } else {
            emit(NetworkResult.success(alertData.body()!!))
        }
    }.catch {
        Log.d("Weather api error", "getAlerts: ${it.message}")
        emit(NetworkResult.Error(0))
    }.catch {
        Log.d("Weather api error", "getAlerts: ${it.message}")
    }

    private fun parseAlerts(jsonObject: JSONObject): List<NetworkResult<AlertModel>> {
        val alertList = mutableListOf<NetworkResult<AlertModel>>()

        val alertsArray = jsonObject.getJSONArray("alerts")
        for (i in 0 until alertsArray.length()) {
            val alertObject = alertsArray.getJSONObject(i)
            val headline = alertObject.getString("headline")
            val category = alertObject.getString("category")
            val event = alertObject.getString("event")
            val effective = alertObject.getString("effective")
            val desc = alertObject.getString("description")

            val alertModel = AlertModel(headline, category, event, effective, desc)

            alertList.add(NetworkResult.Success(alertModel))
        }

        return alertList
    }

}
