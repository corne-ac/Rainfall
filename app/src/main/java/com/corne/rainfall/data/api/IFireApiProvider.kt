package com.corne.rainfall.data.api

import com.corne.rainfall.data.model.FireLocationItemModel
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

/**
 * Defines a Singleton interface for providing access to fire-related data from a remote API.
 */
@Singleton
interface IFireApiProvider {
    /**
     * Fetches fire data from the remote API.
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
    fun getFireData(
        apiKey: String,
        type: String,
        location: String,
        days: Int,
        date: String,
    ): Flow<NetworkResult<List<NetworkResult<FireLocationItemModel>>>>
}
