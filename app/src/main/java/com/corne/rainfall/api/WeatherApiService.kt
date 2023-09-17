package com.corne.rainfall.api

import com.corne.rainfall.data.model.WeatherModel
import retrofit2.http.GET

interface WeatherApiService {
    @GET("test")
    suspend fun get(): List<WeatherModel>
}