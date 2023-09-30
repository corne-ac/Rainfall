package com.corne.rainfall.data.storage

import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RainfallRemoteRepo @Inject constructor() : IRainRepository {
    override fun getRainfallInLocation(locationId: Int): Flow<NetworkResult<List<RainfallData>>> {
        TODO("Not yet implemented")
    }

    override fun getAllRainfallData(): Flow<NetworkResult<List<RainfallData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRainDataBuId(rainfallId: Int): NetworkResult<RainfallData> {
        TODO("Not yet implemented")
    }

    override suspend fun addRainData(rainfallData: RainfallData): NetworkResult<String> {
        TODO("Not yet implemented")
    }

    override fun deleteRainData(rainfallData: RainfallData) {
        TODO("Not yet implemented")
    }

    override fun updateRainData(rainfallData: RainfallData) {
        TODO("Not yet implemented")
    }
}