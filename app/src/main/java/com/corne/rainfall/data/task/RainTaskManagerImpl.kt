package com.corne.rainfall.data.task

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.corne.rainfall.data.worker.RainSyncWorker
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class is an implementation of the IRainTaskManager interface.
 * It provides the functionality to export and import rainfall data using a WorkManager.
 *
 * @property workManager The WorkManager used to schedule background tasks.
 */
@Singleton
class RainTaskManagerImpl @Inject constructor(
    private val workManager: WorkManager,
) : IRainTaskManager {

    /**
     * This method exports rainfall data.
     * It schedules a one-time task using the WorkManager to perform the export operation.
     * The task is set to run when the network is connected.
     */
    override fun exportData() {
        OneTimeWorkRequestBuilder<RainSyncWorker>().setConstraints(getRequiredConstraints()).build()
    }

    /**
     * This method imports rainfall data.
     * The implementation is not yet provided.
     */
    override fun importData() {
        TODO("Not yet implemented")
    }

    /**
     * This method returns the constraints required for the export task.
     * The task requires the network to be connected.
     *
     * @return A Constraints object with the required network type set to CONNECTED.
     */
    private fun getRequiredConstraints(): Constraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
}