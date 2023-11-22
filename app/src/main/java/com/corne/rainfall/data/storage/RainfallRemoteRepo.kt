package com.corne.rainfall.data.storage

import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.data.model.PrefModel
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


// This class is set up in such a manner that we could easily switch between local and remote data storage if we wanted to.
// For now we just want a sync function so many of the functions are not implemented.
class RainfallRemoteRepo @Inject constructor() : IRainRepository {

    // Extra method to sync preferences
    fun syncPreferences(
        prefs: PrefModel,
    ) {
        // Get the user
        // Add the preferences
        // Save these values
    }


    override fun getAllRainfallData(): Flow<NetworkResult<List<RainfallData>>> {
        TODO("Not yet implemented")
    }

    override fun getRainfallInLocation(locationId: Int): Flow<NetworkResult<List<RainfallData>>> {
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

    override fun getAllLocations(): Flow<NetworkResult<List<LocationModel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun addLocation(locationModel: LocationModel): NetworkResult<String> {
        TODO("Not yet implemented")
    }
}