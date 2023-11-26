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

/**
 * This class is an implementation of the IRainRepository interface.
 * It provides the functionality to get and manipulate rainfall data and locations using a RainfallDAO and a LocationDAO.
 *
 * @property rainfallDao The RainfallDAO used to access the rainfall data in the database.
 * @property locationDao The LocationDAO used to access the locations in the database.
 */
class RainfallLocalRepo @Inject constructor(
    private val rainfallDao: RainfallDAO,
    private val locationDao: LocationDAO,
) : IRainRepository {

    /**
     * This method fetches rainfall data for a specific location.
     *
     * @param locationId The UUID of the location.
     * @return A Flow of NetworkResult containing a list of RainfallData.
     */
    override fun getRainfallInLocation(locationId: UUID): Flow<NetworkResult<List<RainfallData>>> =
        rainfallDao.getRainDataByLocation(locationId)
            .map { rainfall -> rainfall.map(toRainfallData) }.transform { rainfall ->
                emit(NetworkResult.Success(rainfall))
            }.catch {
                emit(NetworkResult.success(emptyList()))
            }

    /**
     * This method fetches all rainfall data.
     *
     * @return A Flow of NetworkResult containing a list of RainfallData.
     */
    override fun getAllRainfallData(): Flow<NetworkResult<List<RainfallData>>> =
        rainfallDao.getAllRainData().map { rainfall ->
            rainfall.map(toRainfallData)
        }.transform { rainfall ->
            emit(NetworkResult.Success(rainfall))
        }.catch {
            emit(NetworkResult.success(emptyList()))
        }

    /**
     * This method converts a RainfallData object to a RainfallEntity object.
     *
     * @param rainfallData The RainfallData to be converted.
     * @param locId The UUID of the location.
     * @return A RainfallEntity object.
     */
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

    /**
     * This method fetches a specific rainfall data by its ID.
     *
     * @param rainfallId The UUID of the rainfall data.
     * @return A NetworkResult containing the RainfallData.
     */
    override suspend fun getRainDataBuId(rainfallId: UUID): NetworkResult<RainfallData> {
        TODO("Not yet implemented")
    }

    /**
     * This method adds a new rainfall data to a specific location.
     *
     * @param rainfallData The RainfallData to be added.
     * @param locId The UUID of the location.
     * @return A NetworkResult containing a String message.
     */
    override suspend fun addRainData(rainfallData: RainfallData, locId:UUID): NetworkResult<String> {
        rainfallDao.addRainData(
            toRainfallEntity(rainfallData, locId = locId)
        )
        return NetworkResult.Success("Success")
    }

    /**
     * This method deletes a specific rainfall data.
     *
     * @param rainfallData The RainfallData to be deleted.
     */
    override fun deleteRainData(rainfallData: RainfallData) {
        TODO("Not yet implemented")
    }

    /**
     * This method updates a specific rainfall data.
     *
     * @param rainfallData The RainfallData to be updated.
     */
    override fun updateRainData(rainfallData: RainfallData) {
        TODO("Not yet implemented")
    }

    /**
     * This method fetches all locations.
     *
     * @return A Flow of NetworkResult containing a list of LocationModel.
     */
    override fun getAllLocations(): Flow<NetworkResult<List<LocationModel>>> =
        locationDao.getAllNotificationsForUser().map { locations -> locations.map(toLocationModel) }
            .transform { rainfall ->
                emit(NetworkResult.Success(rainfall))
            }.catch {
                emit(NetworkResult.success(emptyList()))
            }

    /**
     * This method adds a new location.
     *
     * @param locationModel The LocationModel to be added.
     * @return A NetworkResult containing a String message.
     */
    override suspend fun addLocation(locationModel: LocationModel): NetworkResult<String> {
        locationDao.addLocation(LocationEntity(name = locationModel.name))
        return NetworkResult.Success("Success")
    }

    /**
     * This property is a lambda function that converts a LocationEntity object to a LocationModel object.
     */
    private val toLocationModel: (LocationEntity) -> LocationModel = { locationEntity ->
        LocationModel(
            locationEntity.uid, locationEntity.name
        )
    }

    /**
     * This property is a lambda function that converts a RainfallEntity object to a RainfallData object.
     */
    private val toRainfallData: (RainfallEntity) -> RainfallData = { rainfallEntity ->
        RainfallData(
            rainfallEntity.locationUID,
            rainfallEntity.date,
            rainfallEntity.startTime,
            rainfallEntity.endTime,
            rainfallEntity.mm,
            rainfallEntity.notes
        )
    }
}