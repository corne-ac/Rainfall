package com.corne.rainfall.data

import com.corne.rainfall.api.FirmsApiService
import com.corne.rainfall.di.NetworkModuleFirms
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class FirmsRepository {
    public fun fetchFirmsData(
        apiKey: String,
        type: String,
        location: String,
        days: Int,
        date: String,
        onResponse: (String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val service = NetworkModuleFirms.retrofit.create(FirmsApiService::class.java)
        val call = service.getFirmsData(apiKey, type, location, days, date)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle successful response here
                    val responseBody = response.body()?.string()
                    responseBody?.let { onResponse(it) }
                } else {
                    // Handle unsuccessful response here
                    onFailure(Throwable("Unsuccessful response: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network errors here
                onFailure(t)
            }
        })
    }
}