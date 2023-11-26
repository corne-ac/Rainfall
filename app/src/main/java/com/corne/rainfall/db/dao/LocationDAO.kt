package com.corne.rainfall.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.corne.rainfall.db.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

/**
 * This is a DAO (Data Access Object) interface for the LocationEntity.
 * It provides methods to interact with the location data in the database.
 */
@Dao
interface LocationDAO {

    /**
     * This method fetches all locations from the database.
     *
     * @return A Flow of a list of LocationEntity.
     */
    @Query("SELECT * FROM location")
    fun getAllNotificationsForUser(): Flow<List<LocationEntity>>

    /**
     * This method adds a new location to the database.
     *
     * @param location The LocationEntity to be added.
     */
    @Insert
    suspend fun addLocation(location: LocationEntity)
}