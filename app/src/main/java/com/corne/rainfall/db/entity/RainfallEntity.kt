package com.corne.rainfall.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "rainfall")
data class RainfallEntity(
    @ColumnInfo(name = "locationUID") val locationUID: UUID,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "startTime") val startTime: String,
    @ColumnInfo(name = "endTime") val endTime: String,
    @ColumnInfo(name = "mm") val mm: Double,
    @ColumnInfo(name = "notes") val notes: String?,
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
)