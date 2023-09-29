package com.corne.rainfall.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rainfall")
data class RainfallEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "locationUID") val locationUID: Int,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "startTime") val startTime: String,
    @ColumnInfo(name = "endTime") val endTime: String,
    @ColumnInfo(name = "mm") val mm: Double,
    @ColumnInfo(name = "notes") val notes: String?,
)