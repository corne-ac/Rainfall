package com.corne.rainfall.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.corne.rainfall.db.entity.RainfallEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RainfallDAO {

    @Query("SELECT * FROM rainfall")
    fun getAllRainData(): Flow<List<RainfallEntity>>

    @Query("SELECT * FROM rainfall WHERE uid = :rainfallId")
    suspend fun getRainDataById(rainfallId: Int): RainfallEntity

    @Insert
    suspend fun addRainData(rainfallEntity: RainfallEntity)


}