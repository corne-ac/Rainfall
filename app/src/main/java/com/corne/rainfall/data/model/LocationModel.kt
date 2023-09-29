package com.corne.rainfall.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationModel(
    @PrimaryKey val locationUID : Int,
    @ColumnInfo(name = "name") val name: String
    )
