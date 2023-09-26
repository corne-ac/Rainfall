package com.corne.rainfall.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.data.model.LocationWithRainfallModel
import com.corne.rainfall.data.model.RainfallEntryModel

@Database(entities = [LocationModel::class, LocationWithRainfallModel::class, RainfallEntryModel::class], version = 1)
abstract class RainfallDatabase : RoomDatabase() {
    abstract fun rainfallDAO(): RainfallDAO

}