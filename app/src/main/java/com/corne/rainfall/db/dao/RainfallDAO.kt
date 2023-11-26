package com.corne.rainfall.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.corne.rainfall.db.entity.RainfallEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface RainfallDAO {

    @Query("SELECT * FROM rainfall")
    fun getAllRainData(): Flow<List<RainfallEntity>>

    @Query("SELECT * FROM rainfall WHERE locationUID = :locationId")
    fun getRainDataByLocation(locationId: UUID): Flow<List<RainfallEntity>>

    @Query("SELECT * FROM rainfall WHERE uid = :rainfallId")
    suspend fun getRainDataById(rainfallId: UUID): RainfallEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRainData(rainfallEntity: RainfallEntity)

}