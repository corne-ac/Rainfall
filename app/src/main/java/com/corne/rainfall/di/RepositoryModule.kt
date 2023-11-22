package com.corne.rainfall.di

import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.data.storage.RainfallLocalRepo
import com.corne.rainfall.data.storage.RainfallRemoteRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @LocalRainfallRepository
    fun provideLocalRainRepo(local: RainfallLocalRepo): IRainRepository

    @Binds
    @RemoteRainfallRepository
    fun provideRemoteRainRepo(remoteRepository: RainfallRemoteRepo): IRainRepository

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRainfallRepository

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteRainfallRepository
