package com.corne.rainfall.api

import com.corne.rainfall.data.model.AlertResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAlertApiService {
    @GET("forecast.json")
    suspend fun getWeatherAlerts(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: String,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "yes"
    ): Response<AlertResponseModel.AlertsMain>

    //http://api.weatherapi.com/v1/forecast.json?key=9f9a9d1b7bed4c0fa3a120250232511&q=iata:JNB&days=1&aqi=no&alerts=yes

}
