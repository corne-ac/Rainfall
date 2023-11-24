package com.corne.rainfall.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.corne.rainfall.data.model.NotificationType
import java.util.UUID

@Entity(tableName = "notification")
data class NotificationEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "type") val type: NotificationType,
)
