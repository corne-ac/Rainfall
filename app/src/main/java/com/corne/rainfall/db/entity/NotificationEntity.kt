package com.corne.rainfall.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.corne.rainfall.data.model.NotificationType
import java.util.UUID

/**
 * This data class represents a Notification entity in the database.
 * It is annotated with @Entity to indicate that it's a table in the database.
 * Each property of the class represents a column in the table.
 *
 * @property uid The unique identifier of the notification. It is the primary key of the table.
 * @property title The title of the notification.
 * @property body The body content of the notification.
 * @property type The type of the notification, represented by the NotificationType enum.
 */
@Entity(tableName = "notification")
data class NotificationEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "type") val type: NotificationType,
)