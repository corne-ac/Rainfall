package com.corne.rainfall.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FirmsApiService {
    @GET("api/country/csv/{apiKey}/{type}/{location}/{days}/{date}")
    fun getFirmsData(
        @Path("apiKey") apiKey: String,
        @Path("type") type: String,
        @Path("location") location: String,
        @Path("days") days: Int,
        @Path("date") date: String
    ): Call<ResponseBody> //Use ResponseBody for raw CSV data
}