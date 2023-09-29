package com.corne.rainfall.di

import android.app.Application
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.data.preference.RainfallPreferenceImpl
import com.corne.rainfall.data.preference.rainPreferenceDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {
    @Singleton
    @Provides
    fun providePreferenceManager(application: Application): IRainfallPreference {
        return RainfallPreferenceImpl(application.rainPreferenceDataStore)
    }

}