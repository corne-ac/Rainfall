package com.corne.rainfall.data.model

import java.util.Date

/**
 * Data class representing information about rainfall data for a specific date and time range.
 *
 * @property date The date for which rainfall data is recorded.
 * @property startTime The start time of the recorded rainfall period.
 * @property endTime The end time of the recorded rainfall period.
 * @property mm The amount of rainfall measured in millimeters (mm) during the specified period.
 * @property notes Additional notes or comments related to the rainfall data.
 */
data class RainfallData(
    val date: Date,
    val startTime: String,
    val endTime: String,
    val mm: Double,
    val notes: String?,
)