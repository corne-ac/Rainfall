package com.corne.rainfall.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.corne.rainfall.db.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDAO {
    @Query("SELECT * FROM notification")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

}
