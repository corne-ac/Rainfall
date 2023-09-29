package com.corne.rainfall.di

import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.data.storage.RainfallLocalRepo
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
    fun provideRemoteRainRepo(local: RainfallLocalRepo): IRainRepository

//    @Binds
//    @RemoteRainfallRepository
//    fun notyRemoteNoteRepository(remoteRepository: RainfallRemoteRepo): NotyNoteRepository
    /*  @Singleton
      @Provides
      fun provideLocalRainRepo(
      ): IRainRepository {
          return RainfallLocalRepo(context)
      }*/


}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRainfallRepository

/*
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteRainfallRepository
*/
