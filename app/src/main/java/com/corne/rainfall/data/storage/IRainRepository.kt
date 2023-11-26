package com.corne.rainfall.data.storage

import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Singleton

/**
 * This is a singleton interface for the Rain Repository.
 * It provides methods to get and manipulate rainfall data and locations.
 */
@Singleton
interface IRainRepository {
    /**
     * This method fetches rainfall data for a specific location.
     *
     * @param locationId The UUID of the location.
     * @return A Flow of NetworkResult containing a list of RainfallData.
     */
    fun getRainfallInLocation(locationId: UUID): Flow<NetworkResult<List<RainfallData>>>

    /**
     * This method fetches all rainfall data.
     *
     * @return A Flow of NetworkResult containing a list of RainfallData.
     */
    fun getAllRainfallData(): Flow<NetworkResult<List<RainfallData>>>

    /**
     * This method fetches a specific rainfall data by its ID.
     *
     * @param rainfallId The UUID of the rainfall data.
     * @return A NetworkResult containing the RainfallData.
     */
    suspend fun getRainDataBuId(rainfallId: UUID): NetworkResult<RainfallData>

    /**
     * This method adds a new rainfall data to a specific location.
     *
     * @param rainfallData The RainfallData to be added.
     * @param locId The UUID of the location.
     * @return A NetworkResult containing a String message.
     */
    suspend fun addRainData(rainfallData: RainfallData,locId:UUID ): NetworkResult<String>

    /**
     * This method deletes a specific rainfall data.
     *
     * @param rainfallData The RainfallData to be deleted.
     */
    fun deleteRainData(rainfallData: RainfallData)

    /**
     * This method updates a specific rainfall data.
     *
     * @param rainfallData The RainfallData to be updated.
     */
    fun updateRainData(rainfallData: RainfallData)

    /**
     * This method fetches all locations.
     *
     * @return A Flow of NetworkResult containing a list of LocationModel.
     */
    fun getAllLocations(): Flow<NetworkResult<List<LocationModel>>>

    /**
     * This method adds a new location.
     *
     * @param locationModel The LocationModel to be added.
     * @return A NetworkResult containing a String message.
     */
    suspend fun addLocation(locationModel: LocationModel): NetworkResult<String>
}