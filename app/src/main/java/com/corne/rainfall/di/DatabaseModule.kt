package com.corne.rainfall.di

import android.app.Application
import com.corne.rainfall.db.RainfallDatabase
import com.corne.rainfall.db.dao.LocationDAO
import com.corne.rainfall.db.dao.NotificationDAO
import com.corne.rainfall.db.dao.RainfallDAO
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

    @Singleton
    @Provides
    fun providesLocationDao(db: RainfallDatabase): LocationDAO {
        return db.locationDao()
    }

    @Singleton
    @Provides
    fun providesNotificationDao(db: RainfallDatabase): NotificationDAO {
        return db.notificationDAO()
    }

}