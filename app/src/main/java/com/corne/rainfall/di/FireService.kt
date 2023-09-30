package com.corne.rainfall.di

import com.corne.rainfall.data.api.FireApiProviderImpl
import com.corne.rainfall.data.api.IFireApiProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FireService {
    @Provides
    @Singleton
    fun provideFireService(fireApiProvider: FireApiProviderImpl): IFireApiProvider = fireApiProvider

}