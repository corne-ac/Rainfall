package com.corne.rainfall.data.model

import java.util.UUID

data class PrefModel(
    val darkMode: Boolean = false,
    val language: String = "en",
    val offline: Boolean = false,
    val location: UUID? = null,
    val graphType: Boolean = false,
    val lastUpdated: Long?,
)
