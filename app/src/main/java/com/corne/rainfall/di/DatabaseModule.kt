package com.corne.rainfall.di

import android.app.Application
import com.corne.rainfall.db.dao.RainfallDAO
import com.corne.rainfall.db.RainfallDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providesRoomDatabase(app: Application): RainfallDatabase {
        return RainfallDatabase.getInstance(app)
    }

    @Singleton
    @Provides
    fun providesRainDao(db: RainfallDatabase): RainfallDAO {
        return db.rainEntityDao()
    }


}