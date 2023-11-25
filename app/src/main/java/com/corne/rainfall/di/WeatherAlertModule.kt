package com.corne.rainfall.di

import com.corne.rainfall.api.WeatherAlertApiService
import com.corne.rainfall.data.api.IWeatherAlertApiProvider
import com.corne.rainfall.data.api.WeatherAlertApiProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherAlertModule {
    @Provides
    @Singleton
    fun provideWeatherAlertApiProvider(apiService: WeatherAlertApiService): IWeatherAlertApiProvider {
        return WeatherAlertApiProviderImpl(apiService)
    }
}
