package com.corne.rainfall.di

import com.corne.rainfall.api.FirmsApiService
import com.corne.rainfall.api.WeatherAlertApiService
import com.corne.rainfall.api.WeatherApiService
import com.corne.rainfall.utils.Constants
import com.squareup.moshi.Moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


//The Below network module code structure was derived from Medium
//https://medium.com/codex/creating-the-network-module-with-hilt-3eefc54b72
//Priyansh Kedia
//https://medium.com/@priyansh-kedia

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val okHttpClientBuilder: OkHttpClient.Builder =
        OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES)

    private val moshi = Builder().add(KotlinJsonAdapterFactory()).build()

    private val fireRetrofitInstance: Retrofit.Builder =
        Retrofit.Builder().baseUrl(Constants.FIRMS_BASE_URL).client(okHttpClientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))

    @Provides
    @Singleton
    fun providesFireApiService(): FirmsApiService =
        fireRetrofitInstance.build().create(FirmsApiService::class.java)

    private val weatherRetrofitInstance: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesWeatherApiService(): WeatherApiService {
        return weatherRetrofitInstance.create(WeatherApiService::class.java)
    }

    private val weatherAlertRetrofitInstance: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.WEATHER_ALERTS_BASE_URL)
        .client(okHttpClientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesWeatherAlertApiService(): WeatherAlertApiService {
        return weatherAlertRetrofitInstance.create(WeatherAlertApiService::class.java)
    }

}