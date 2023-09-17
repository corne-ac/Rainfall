package com.corne.rainfall.di

import android.app.Application
import com.corne.rainfall.data.IRainfallPreferenceManager
import com.corne.rainfall.data.RainfallPreferenceManagerImpl
import com.corne.rainfall.data.rainPreferenceDataStore
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
    fun providePreferenceManager(application: Application): IRainfallPreferenceManager {
        return RainfallPreferenceManagerImpl(application.rainPreferenceDataStore)
    }

}