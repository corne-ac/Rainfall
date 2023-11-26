package com.corne.rainfall.data.storage

import com.corne.rainfall.R
import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.data.model.PrefModel
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.utils.FirebaseHelper
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

/**
 * This class is an implementation of the IRainRepository interface.
 * It provides the functionality to get and manipulate rainfall data and locations using Firebase.
 * It is set up in such a manner that we could easily switch between local and remote data storage if we wanted to.
 * For now we just want a sync function so many of the functions are not implemented.
 */
class RainfallRemoteRepo @Inject constructor() : IRainRepository {

    /**
     * This method syncs user preferences with Firebase.
     *
     * @param prefs The PrefModel containing the user preferences.
     */
    fun syncPreferences(
        prefs: PrefModel,
    ) {
        // Get the user
        // Add the preferences
        // Save these values
    }

    /**
     * This method fetches user preferences from Firebase.
     *
     * @return A Flow of NetworkResult containing the PrefModel.
     */
    fun downloadPreferences(): Flow<NetworkResult<PrefModel>> = flow {
        // Get the user
        val value: NetworkResult<PrefModel> = NetworkResult.Success(
            FirebaseHelper.currentUserPreferencesDocRef.get().await()
                .toObject(PrefModel::class.java)!!
        )
        emit(value)
    }.catch {
        emit(NetworkResult.error(R.string.preferences_error))
    }

    /**
     * This method fetches all rainfall data from Firebase.
     *
     * @return A Flow of NetworkResult containing a list of RainfallData.
     */
    override fun getAllRainfallData(): Flow<NetworkResult<List<RainfallData>>> = flow {
        val docs = FirebaseHelper.currentUserRainDataDocRef.get().await()
        val rainData = mutableListOf<RainfallData>()
        for (doc in docs) {
            val data = doc.toObject(RainfallData::class.java)
            rainData.add(data)
        }
        emit(NetworkResult.Success(rainData.toList()))
    }.catch {
        emit(NetworkResult.success(emptyList()))
    }

    /**
     * This method fetches all locations from Firebase.
     *
     * @return A Flow of NetworkResult containing a list of LocationModel.
     */
    override fun getAllLocations(): Flow<NetworkResult<List<LocationModel>>> = flow {
        val docs = FirebaseHelper.currentUserRainDataDocRef.get().await()
        val rainData = mutableListOf<LocationModel>()
        for (doc in docs) {
            val data = doc.toObject(LocationModel::class.java)
            rainData.add(data)
        }
        emit(NetworkResult.Success(rainData.toList()))
    }.catch {
        emit(NetworkResult.success(emptyList()))
    }

    // The following methods are not yet implemented.

    override fun getRainfallInLocation(locationId: UUID): Flow<NetworkResult<List<RainfallData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRainDataBuId(rainfallId: UUID): NetworkResult<RainfallData> {
        TODO("Not yet implemented")
    }

    override suspend fun addRainData(
        rainfallData: RainfallData,
        locId: UUID,
    ): NetworkResult<String> {
        TODO("Not yet implemented")
    }

    override fun deleteRainData(rainfallData: RainfallData) {
        TODO("Not yet implemented")
    }

    override fun updateRainData(rainfallData: RainfallData) {
        TODO("Not yet implemented")
    }

    override suspend fun addLocation(locationModel: LocationModel): NetworkResult<String> {
        TODO("Not yet implemented")
    }

    /**
     * This method uploads user preferences to Firebase.
     *
     * @param prefModel The PrefModel containing the user preferences.
     */
    fun uploadPreferences(prefModel: PrefModel) {
        FirebaseHelper.currentUserPreferencesDocRef.set(prefModel)
    }
}