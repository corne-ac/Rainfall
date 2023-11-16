package com.corne.rainfall.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.corne.rainfall.db.entity.LocationEntity
import com.corne.rainfall.db.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDAO {
    @Query("SELECT * FROM location where  userId= :userId")
    fun getAllNotificationsForUser(userId: Int): Flow<List<LocationEntity>>

}