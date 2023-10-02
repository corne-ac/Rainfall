package com.corne.rainfall.api

import com.corne.rainfall.data.model.WeatherModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {
    @GET("map/{layer}/{z}/{x}/{y}.png")
    fun getWeatherLayer(
        @Path("layer") layer: String,
        @Path("z") z: String,
        @Path("x") x: String,
        @Path("y") y: String,
        @Query("appid") apiKey: String
    ): Call<ResponseBody> //Use ResponseBody since it's an image
}