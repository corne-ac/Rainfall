package com.corne.rainfall.data

import com.corne.rainfall.data.model.RainfallEntryModel
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface IRainRepository {
    fun getRainData(): Flow<List<RainfallEntryModel>>

    fun addRainData(rainfallEntryModel: RainfallEntryModel): NetworkResult<String>

    fun deleteRainData(rainfallEntryModel: RainfallEntryModel)

    fun updateRainData(rainfallEntryModel: RainfallEntryModel)

    fun getRainDataById(id: Int): RainfallEntryModel

    fun getRainDataByName(name: String): RainfallEntryModel

    fun getRainDataByAmount(amountInMl: Double): RainfallEntryModel


}