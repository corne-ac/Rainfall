package com.corne.rainfall.di

import android.app.Application
import com.corne.rainfall.data.conection.ConnectedObserverImpl
import com.corne.rainfall.data.conection.IConnectedObserver
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.data.preference.RainfallPreferenceImpl
import com.corne.rainfall.data.preference.rainPreferenceDataStore
import com.corne.rainfall.utils.connectivityManager
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


    // Not too sure if this is the best place to do this, we might want to move this to a 'Connected module'
    @Singleton
    @Provides
    fun provideConnectedObserver(application: Application): IConnectedObserver {
        return ConnectedObserverImpl(application.connectivityManager)
    }

}