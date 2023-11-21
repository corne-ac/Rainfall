package com.corne.rainfall.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.corne.rainfall.db.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDAO {
    @Query("SELECT * FROM location")
    fun getAllNotificationsForUser(): Flow<List<LocationEntity>>
}