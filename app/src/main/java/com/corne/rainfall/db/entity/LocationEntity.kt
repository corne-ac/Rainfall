package com.corne.rainfall.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * This data class represents a Location entity in the database.
 * It is annotated with @Entity to indicate that it's a table in the database.
 * Each property of the class represents a column in the table.
 *
 * @property uid The unique identifier of the location. It is the primary key of the table.
 * @property name The name of the location.
 */
@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "name") val name: String,
)