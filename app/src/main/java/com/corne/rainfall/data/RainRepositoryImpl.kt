package com.corne.rainfall.data

import com.corne.rainfall.data.model.RainData
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RainRepositoryImpl : IRainRepository {
    override fun getRainData(): Flow<List<RainData>> = flow {
        emit(listOf(RainData(1.0, "test")))
    }

    override fun addRainData(rainData: RainData): NetworkResult<String> {
        // TODO here we will do the actual saving of the data

        return NetworkResult.success("Success")
    }

    override fun deleteRainData(rainData: RainData) {
        TODO("Not yet implemented")
    }

    override fun updateRainData(rainData: RainData) {
        TODO("Not yet implemented")
    }

    override fun getRainDataById(id: Int): RainData {
        TODO("Not yet implemented")
    }

    override fun getRainDataByName(name: String): RainData {
        TODO("Not yet implemented")
    }

    override fun getRainDataByAmount(amountInMl: Double): RainData {
        TODO("Not yet implemented")
    }
}