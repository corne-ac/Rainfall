package com.corne.rainfall.data.storage

import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.db.dao.LocationDAO
import com.corne.rainfall.db.dao.RainfallDAO
import com.corne.rainfall.db.entity.LocationEntity
import com.corne.rainfall.db.entity.RainfallEntity
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import java.util.UUID
import javax.inject.Inject

class RainfallLocalRepo @Inject constructor(
    private val rainfallDao: RainfallDAO,
    private val locationDao: LocationDAO,
) : IRainRepository {
    override fun getRainfallInLocation(locationId: UUID): Flow<NetworkResult<List<RainfallData>>> =
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


    private fun toRainfallEntity(rainfallData: RainfallData, locId: UUID): RainfallEntity {
        return RainfallEntity(
            locId,
            rainfallData.date,
            rainfallData.endTime,
            rainfallData.startTime,
            rainfallData.mm,
            rainfallData.notes
        )
    }

    override suspend fun getRainDataBuId(rainfallId: UUID): NetworkResult<RainfallData> {

        TODO("Not yet implemented")/*
                val rainfallEntity = rainfallDao.getRainDataById(rainfallId)
                return NetworkResult.Success(
                    RainData(
                        rainfallEntity.mm,
                        rainfallEntity.startTime
                    )
                )*/
    }

    override suspend fun addRainData(rainfallData: RainfallData): NetworkResult<String> {
        //TODO: Add location id
        rainfallDao.addRainData(
            toRainfallEntity(rainfallData, UUID.randomUUID())
        )
        return NetworkResult.Success("Success")
    }

    override fun deleteRainData(rainfallData: RainfallData) {
        TODO("Not yet implemented")
    }

    override fun updateRainData(rainfallData: RainfallData) {
        TODO("Not yet implemented")
    }


    override fun getAllLocations(): Flow<NetworkResult<List<LocationModel>>> =
        locationDao.getAllNotificationsForUser().map { locations -> locations.map(toLocationModel) }
            .transform { rainfall ->
                emit(NetworkResult.Success(rainfall))
            }.catch {
                emit(NetworkResult.success(emptyList()))
            }

    override suspend fun addLocation(locationModel: LocationModel): NetworkResult<String> {
        locationDao.addLocation(LocationEntity(name = locationModel.name))
        return NetworkResult.Success("Success")
    }

    private val toLocationModel: (LocationEntity) -> LocationModel = { locationEntity ->
        LocationModel(
            locationEntity.uid, locationEntity.name
        )
    }
    private val toRainfallData: (RainfallEntity) -> RainfallData = { rainfallEntity ->
        RainfallData(
            rainfallEntity.date,
            rainfallEntity.startTime,
            rainfallEntity.endTime,
            rainfallEntity.mm,
            rainfallEntity.startTime
        )
    }
}