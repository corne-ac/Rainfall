package com.corne.rainfall.data

import com.corne.rainfall.data.model.RainfallEntryModel
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RainRepositoryImpl : IRainRepository {
    override fun getRainData(): Flow<List<RainfallEntryModel>> = flow {
//        emit(listOf(RainfallEntryModel(1.0, "test")))
    }

    override fun addRainData(rainfallEntryModel: RainfallEntryModel): NetworkResult<String> {
        // TODO here we will do the actual saving of the data

        return NetworkResult.success("Success")
    }

    override fun deleteRainData(rainfallEntryModel: RainfallEntryModel) {
        TODO("Not yet implemented")
    }

    override fun updateRainData(rainfallEntryModel: RainfallEntryModel) {
        TODO("Not yet implemented")
    }

    override fun getRainDataById(id: Int): RainfallEntryModel {
        TODO("Not yet implemented")
    }

    override fun getRainDataByName(name: String): RainfallEntryModel {
        TODO("Not yet implemented")
    }

    override fun getRainDataByAmount(amountInMl: Double): RainfallEntryModel {
        TODO("Not yet implemented")
    }
}