package com.corne.rainfall.data

import com.corne.rainfall.data.model.RainData
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface IRainRepository {
    fun getRainData(): Flow<List<RainData>>

    fun addRainData(rainData: RainData): NetworkResult<String>

    fun deleteRainData(rainData: RainData)

    fun updateRainData(rainData: RainData)

    fun getRainDataById(id: Int): RainData

    fun getRainDataByName(name: String): RainData

    fun getRainDataByAmount(amountInMl: Double): RainData


}