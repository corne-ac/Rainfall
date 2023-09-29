package com.corne.rainfall.di

import okhttp3.OkHttpClient
import retrofit2.Retrofit

object NetworkModuleFirms {
    private const val FIRMS_BASE_URL = "https://firms.modaps.eosdis.nasa.gov/"

    private val client = OkHttpClient.Builder().build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(FIRMS_BASE_URL)
        .client(client)
        .build()
}