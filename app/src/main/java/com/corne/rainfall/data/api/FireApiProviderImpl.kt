package com.corne.rainfall.data.api

import android.util.Log
import com.corne.rainfall.api.FirmsApiService
import com.corne.rainfall.data.model.FireLocationItemModel
import com.corne.rainfall.utils.CsvParser
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * Implementation of the [IFireApiProvider] interface that provides access to fire-related data
 * from a remote API.
 *
 * @param fireService An instance of [FirmsApiService] used to make API requests.
 */
class FireApiProviderImpl @Inject constructor(
    private val fireService: FirmsApiService,
) : IFireApiProvider {

    /**
     * Fetches fire-related data from the remote API.
     *
     * @param apiKey The API key required to authenticate and access the API.
     * @param type The type of fire data to retrieve (e.g., "MODIS_NRT").
     * @param location The location for which fire data is requested (e.g., "ZAF").
     * @param days The number of past days for which fire data is needed.
     * @param date The specific date for which fire data is requested.
     *
     * @return A [Flow] of [NetworkResult] containing a list of [FireLocationItemModel].
     * The [Flow] emits the result of the API call asynchronously.
     */
    override fun getFireData(
        apiKey: String,
        type: String,
        location: String,
        days: Int,
        date: String,
    ): Flow<NetworkResult<List<NetworkResult<FireLocationItemModel>>>> = flow {
        val fireData = fireService.getFirmsData(apiKey, type, location, days, date)
        if (!fireData.isSuccessful) {

            // TODO: I18N for error message formatted with response code
            emit(NetworkResult.Error(fireData.code()))
            return@flow
        }
        val fireDataString = fireData.body()?.string()
        if (fireDataString == null) {
            //TODO: error message
            emit(NetworkResult.Error(0))
            return@flow
        }
        // TODO: CsvParser.parseCsvData() should return a NetworkResult and we should emit that here
        val data = CsvParser.parseCsvData(fireDataString).toList()
        emit(NetworkResult.Success(data))

    }.catch {
        // TODO: error message I18N
        emit(NetworkResult.Error(0))
    }.catch {
        Log.d("FireApiProviderImpl", "getFireData: ${it.message}")
    }


}