package com.corne.rainfall.data.storage

import com.corne.rainfall.data.model.RainData
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface IRainRepository {
    fun getRainfallInLocation(locationId: Int): Flow<List<RainData>>
    fun getAllRainfallData(): Flow<List<RainData>>

    suspend fun getRainDataBuId(rainfallId: Int): NetworkResult<RainData>
    suspend fun addRainData(rainData: RainData): NetworkResult<String>

    fun deleteRainData(rainData: RainData)

    fun updateRainData(rainData: RainData)

}