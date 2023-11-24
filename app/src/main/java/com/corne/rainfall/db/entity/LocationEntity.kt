package com.corne.rainfall.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "name") val name: String,
)