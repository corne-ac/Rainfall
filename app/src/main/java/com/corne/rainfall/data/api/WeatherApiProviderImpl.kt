package com.corne.rainfall.data.api

import android.util.Log
import com.corne.rainfall.api.WeatherAlertApiService
import com.corne.rainfall.data.model.AlertModel
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject

class WeatherAlertApiProviderImpl@Inject constructor(
    private val weatherAlertApiService: WeatherAlertApiService,
) : IWeatherAlertApiProvider {

    override fun getAlerts(
        apiKey: String,
        location: String,
        days: String
    ): Flow<NetworkResult<List<NetworkResult<AlertModel>>>> = flow {

        val alertData = weatherAlertApiService.getWeatherAlerts(apiKey, "iata:JNB", days, "no", "yes")

        if (!alertData.isSuccessful) {
            emit(NetworkResult.Error(alertData.code()))
            return@flow
        }
        val alertString = alertData.body()?.toString()
        if (alertString == null) {
            //TODO: error message
            emit(NetworkResult.Error(0))
            return@flow
        }

        val jsondata = JSONObject(alertData.body().toString())
        val alertList = parseAlerts(jsondata)

        emit(NetworkResult.Success(alertList))

    }.catch {
        emit(NetworkResult.Error(0))
    }.catch {
        Log.d("WeatherAlertApiProviderImpl", "getAlerts: ${it.message}")
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
