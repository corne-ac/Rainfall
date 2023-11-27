package com.corne.rainfall.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.corne.rainfall.db.entity.RainfallEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * This is a DAO (Data Access Object) interface for the RainfallEntity.
 * It provides methods to interact with the rainfall data in the database.
 */
@Dao
interface RainfallDAO {

    /**
     * This method fetches all rainfall data from the database.
     *
     * @return A Flow of a list of RainfallEntity.
     */
    @Query("SELECT * FROM rainfall")
    fun getAllRainData(): Flow<List<RainfallEntity>>

    /**
     * This method fetches rainfall data for a specific location from the database.
     *
     * @param locationId The UUID of the location.
     * @return A Flow of a list of RainfallEntity.
     */
    @Query("SELECT * FROM rainfall WHERE locationUID = :locationId")
    fun getRainDataByLocation(locationId: UUID): Flow<List<RainfallEntity>>

    /**
     * This method fetches a specific rainfall data by its ID from the database.
     *
     * @param rainfallId The UUID of the rainfall data.
     * @return A RainfallEntity object.
     */
    @Query("SELECT * FROM rainfall WHERE uid = :rainfallId")
    suspend fun getRainDataById(rainfallId: UUID): RainfallEntity

    /**
     * This method adds a new rainfall data to the database.
     *
     * @param rainfallEntity The RainfallEntity to be added.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRainData(rainfallEntity: RainfallEntity)
}