package com.corne.rainfall.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.corne.rainfall.data.model.PrefModel
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.data.preference.IRainfallPreference
import com.corne.rainfall.data.storage.IRainRepository
import com.corne.rainfall.data.storage.RainfallRemoteRepo
import com.corne.rainfall.data.task.IRainTaskManager
import com.corne.rainfall.di.LocalRainfallRepository
import com.corne.rainfall.di.RemoteRainfallRepository
import com.corne.rainfall.utils.NetworkResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class RainSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @RemoteRainfallRepository private val remoteRepo: IRainRepository,
    @LocalRainfallRepository private val localRepo: IRainRepository,
    private val rainfallPreference: IRainfallPreference,
    private val rainTaskManager: IRainTaskManager,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return syncRainData()
    }


    private suspend fun syncRainData(): Result {
        // TODO: upload user data/ download user data??
        // Fetch data from online
        return try {
            fetchRemoteRain()
            if (remoteRepo is RainfallRemoteRepo) {
                val darkMode = rainfallPreference.uiModeFlow.first()
                val language = rainfallPreference.languageModeFlow.first()
                val offline = rainfallPreference.offlineModeFlow.first()
                val location = rainfallPreference.defaultLocationFlow.first()
                val graphType = rainfallPreference.defaultGraphFlow.first()
                remoteRepo.syncPreferences(
                    PrefModel(darkMode, language, offline, location, graphType)
                )
            }

            // Local should always have the latest data unless the user has not synced before??.


            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private suspend fun fetchRemoteRain(): List<RainfallData> {
        return when (val response = remoteRepo.getAllRainfallData().first()) {
            is NetworkResult.Success -> response.data
            is NetworkResult.Error -> throw Exception()
        }
    }


}