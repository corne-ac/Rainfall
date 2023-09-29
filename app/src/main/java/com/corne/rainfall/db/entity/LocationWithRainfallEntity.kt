package com.corne.rainfall.db.entity

import androidx.room.Embedded
import androidx.room.Relation


data class LocationWithRainfallEntity(
    @Embedded val locationModel: LocationEntity,
    @Relation(
        parentColumn = "locationUID", entityColumn = "uid"
    ) val rainfallEntries: List<RainfallEntity>,
)