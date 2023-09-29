package com.corne.rainfall.data.storage

import com.corne.rainfall.data.model.RainData
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RainfallRemoteRepo @Inject constructor() : IRainRepository {
    override fun getRainfallInLocation(locationId: Int): Flow<List<RainData>> {
        TODO("Not yet implemented")
    }

    override fun getAllRainfallData(): Flow<List<RainData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRainDataBuId(rainfallId: Int): NetworkResult<RainData> {
        TODO("Not yet implemented")
    }

    override suspend fun addRainData(rainData: RainData): NetworkResult<String> {
        TODO("Not yet implemented")
    }

    override fun deleteRainData(rainData: RainData) {
        TODO("Not yet implemented")
    }

    override fun updateRainData(rainData: RainData) {
        TODO("Not yet implemented")
    }
}