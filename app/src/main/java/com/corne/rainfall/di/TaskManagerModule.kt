package com.corne.rainfall.di

import android.app.Application
import androidx.work.WorkManager
import com.corne.rainfall.data.task.IRainTaskManager
import com.corne.rainfall.data.task.RainTaskManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TaskManagerModule {
    @Binds
    fun provideRainTaskManager(rainTaskManager: RainTaskManagerImpl): IRainTaskManager


}