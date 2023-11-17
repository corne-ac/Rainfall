package com.corne.rainfall.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {
    @GET("map/{layer}/{z}/{x}/{y}.png")
    fun getWeatherLayer(
        @Path("layer") layer: String,
        @Path("z") z: Int,
        @Path("x") x: Int,
        @Path("y") y: Int,
        @Query("appid") apiKey: String
    ): Call<ResponseBody> //Use ResponseBody since it's an image
}