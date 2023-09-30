package com.corne.rainfall.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API service interface for fetching fire-related data.
 */
interface FirmsApiService {

    /**
     * Retrieves fire-related data from the remote API.
     *
     * @param apiKey The API key required to authenticate and access the API.
     * @param type The type of fire data to retrieve (e.g., "MODIS_NRT").
     * @param location The location for which fire data is requested (e.g., "ZAF").
     * @param days The number of past days for which fire data is needed.
     * @param date The specific date for which fire data is requested.
     *
     * @return A [Response] containing the raw CSV data of fire-related information. Use [ResponseBody]
     * for handling the raw CSV data later.
     */
    @GET("api/country/csv/{apiKey}/{type}/{location}/{days}/{date}")
    suspend fun getFirmsData(
        @Path("apiKey") apiKey: String,
        @Path("type") type: String,
        @Path("location") location: String,
        @Path("days") days: Int,
        @Path("date") date: String,
    ): Response<ResponseBody> //Use ResponseBody for raw CSV data
}