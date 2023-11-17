package com.corne.rainfall.data.storage

import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.db.dao.RainfallDAO
import com.corne.rainfall.db.entity.RainfallEntity
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import java.util.Date
import javax.inject.Inject

class RainfallLocalRepo @Inject constructor(
    private val rainfallDao: RainfallDAO,
) : IRainRepository {
    override fun getRainfallInLocation(locationId: Int): Flow<NetworkResult<List<RainfallData>>> =
        rainfallDao.getRainDataByLocation(locationId)
            .map { rainfall -> rainfall.map(toRainfallData) }.transform { rainfall ->
                emit(NetworkResult.Success(rainfall))
            }.catch {
                emit(NetworkResult.success(emptyList()))
            }

    override fun getAllRainfallData(): Flow<NetworkResult<List<RainfallData>>> =
        rainfallDao.getAllRainData().map { rainfall ->
            rainfall.map(toRainfallData)
        }.transform { rainfall ->
            emit(NetworkResult.Success(rainfall))
        }.catch {
            emit(NetworkResult.success(emptyList()))
        }

    private val toRainfallData: (RainfallEntity) -> RainfallData = { rainfallEntity ->
        RainfallData(
            Date(rainfallEntity.date),
            rainfallEntity.startTime,
            rainfallEntity.endTime,
            rainfallEntity.mm,
            rainfallEntity.startTime
        )
    }

    private fun toRainfallEntity(rainfallData: RainfallData, locId: Int): RainfallEntity {
        return RainfallEntity(
            locId,
            rainfallData.date.time,
            rainfallData.endTime,
            rainfallData.startTime,
            rainfallData.mm,
            rainfallData.notes
        )
    }

    override suspend fun getRainDataBuId(rainfallId: Int): NetworkResult<RainfallData> {
        TODO("Not yet implemented")/*  val rainfallEntity = rainfallDao.getRainDataById(rainfallId)
          return NetworkResult.Success(
              RainData(
                  rainfallEntity.mm,
                  rainfallEntity.startTime
              )
          )*/
    }

    override suspend fun addRainData(rainfallData: RainfallData): NetworkResult<String> {
        rainfallDao.addRainData(
            toRainfallEntity(rainfallData, 1)
        )
        return NetworkResult.Success("Success")
    }

    override fun deleteRainData(rainfallData: RainfallData) {
        TODO("Not yet implemented")
    }

    override fun updateRainData(rainfallData: RainfallData) {
        TODO("Not yet implemented")
    }
}