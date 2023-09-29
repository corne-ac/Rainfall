package com.corne.rainfall.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time

//@Entity(
//    foreignKeys = [ForeignKey(
//        entity = LocationModel::class,
//        parentColumns = arrayOf("locationUID"),
//        childColumns = arrayOf("artist"),
//        onDelete = ForeignKey.CASCADE
//    )]
//)
@Entity
data class RainfallEntryModel(
    @PrimaryKey val entryUID : Int,
    @ColumnInfo(name = "locationUID") val locationUID: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "startTime") val startTime: String,
    @ColumnInfo(name = "endTime") val endTime: String,
    @ColumnInfo(name = "mm") val mm: Double,
    @ColumnInfo(name = "notes") val notes: String?
)