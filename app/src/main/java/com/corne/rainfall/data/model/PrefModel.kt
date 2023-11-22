package com.corne.rainfall.data.model

data class PrefModel(
    val darkMode: Boolean = false,
    val language: String = "en",
    val offline: Boolean = false,
    val location: Int = -1,
    val graphType: Boolean = false,
)
