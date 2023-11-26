package com.corne.rainfall.data.task

/**
 * This is an interface for managing tasks related to rainfall data.
 * It provides methods to export and import data.
 */
interface IRainTaskManager {
    /**
     * This method exports rainfall data.
     * The implementation should handle the specifics of how and where the data is exported.
     */
    fun exportData()

    /**
     * This method imports rainfall data.
     * The implementation should handle the specifics of how and where the data is imported from.
     */
    fun importData()
}