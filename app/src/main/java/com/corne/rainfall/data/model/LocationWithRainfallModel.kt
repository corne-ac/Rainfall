package com.corne.rainfall.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class LocationWithRainfallModel(
    @Embedded val locationModel: LocationModel,
    @PrimaryKey val entryUID : Int
//    @Relation(
//        parentColumn = "locationUID",
//        entityColumn = "locationUID"
//    )
   // val rainfallEntries : List<RainfallEntryModel>
)
