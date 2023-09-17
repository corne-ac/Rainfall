package com.corne.rainfall.di

import com.corne.rainfall.data.IRainRepository
import com.corne.rainfall.data.RainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun provideRainRepo(): IRainRepository {
        return RainRepositoryImpl()
    }

}