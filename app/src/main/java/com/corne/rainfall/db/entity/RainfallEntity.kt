package com.corne.rainfall.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

/**
 * This data class represents a Rainfall entity in the database.
 * It is annotated with @Entity to indicate that it's a table in the database.
 * Each property of the class represents a column in the table.
 *
 * @property locationUID The unique identifier of the location associated with the rainfall data.
 * @property date The date of the rainfall data.
 * @property startTime The start time of the rainfall data.
 * @property endTime The end time of the rainfall data.
 * @property mm The amount of rainfall in millimeters.
 * @property notes Any additional notes regarding the rainfall data.
 * @property uid The unique identifier of the rainfall data. It is the primary key of the table.
 */
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