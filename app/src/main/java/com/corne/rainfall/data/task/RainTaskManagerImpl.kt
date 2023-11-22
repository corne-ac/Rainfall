package com.corne.rainfall.data.task

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.corne.rainfall.data.worker.RainSyncWorker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RainTaskManagerImpl @Inject constructor(
    private val workManager: WorkManager
) : IRainTaskManager {
    override fun exportData() {
        OneTimeWorkRequestBuilder<RainSyncWorker>().setConstraints(getRequiredConstraints()).build()
    }

    override fun importData() {
        TODO("Not yet implemented")
    }

    private fun getRequiredConstraints(): Constraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
}