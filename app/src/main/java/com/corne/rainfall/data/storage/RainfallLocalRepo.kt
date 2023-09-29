package com.corne.rainfall.data.storage

import com.corne.rainfall.data.model.RainData
import com.corne.rainfall.db.dao.RainfallDAO
import com.corne.rainfall.db.entity.RainfallEntity
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RainfallLocalRepo @Inject constructor(
    private val rainfallDao: RainfallDAO,
) : IRainRepository {
    override fun getRainfallInLocation(locationId: Int): Flow<List<RainData>> {
        TODO("Not yet implemented")
    }

    override fun getAllRainfallData(): Flow<List<RainData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRainDataBuId(rainfallId: Int): NetworkResult<RainData> {
        TODO("Not yet implemented")
      /*  val rainfallEntity = rainfallDao.getRainDataById(rainfallId)
        return NetworkResult.Success(
            RainData(
                rainfallEntity.mm,
                rainfallEntity.startTime
            )
        )*/
    }

    override suspend fun addRainData(rainData: RainData): NetworkResult<String> {
        rainfallDao.addRainData(
            RainfallEntity(
                1, 1, 1, "", "", 10.0, ""
            )
        )
        return NetworkResult.Success("Success")
    }

    override fun deleteRainData(rainData: RainData) {
        TODO("Not yet implemented")
    }

    override fun updateRainData(rainData: RainData) {
        TODO("Not yet implemented")
    }
}