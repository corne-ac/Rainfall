package com.corne.rainfall.data.storage

import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Singleton

@Singleton
interface IRainRepository {
    fun getRainfallInLocation(locationId: UUID): Flow<NetworkResult<List<RainfallData>>>
    fun getAllRainfallData(): Flow<NetworkResult<List<RainfallData>>>

    suspend fun getRainDataBuId(rainfallId: UUID): NetworkResult<RainfallData>
    suspend fun addRainData(rainfallData: RainfallData): NetworkResult<String>

    fun deleteRainData(rainfallData: RainfallData)

    fun updateRainData(rainfallData: RainfallData)

    fun getAllLocations(): Flow<NetworkResult<List<LocationModel>>>
    suspend fun addLocation(locationModel: LocationModel): NetworkResult<String>
}