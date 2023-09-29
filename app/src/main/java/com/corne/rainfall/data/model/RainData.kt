package com.corne.rainfall.data.model

import java.util.Date

data class RainData(
    val date: Date,
    val startTime: String,
    val endTime: String,
    val mm: Double,
    val notes: String,
)