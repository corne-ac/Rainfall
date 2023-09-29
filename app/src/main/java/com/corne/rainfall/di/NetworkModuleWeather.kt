package com.corne.rainfall.di

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
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModuleWeather {
    private val okHttpClientBuilder: OkHttpClient.Builder =
        OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES)

    private val moshi = Builder().add(KotlinJsonAdapterFactory()).build()

    private val baseRetrofitBuilder: Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Constants.API_WEATHER_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))

    @Singleton
    @Provides
    fun providesWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }


}