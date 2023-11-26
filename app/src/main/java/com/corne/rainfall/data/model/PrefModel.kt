package com.corne.rainfall.data.model

import java.util.UUID

/**
 * This data class represents a Preference model.
 * It contains various properties related to user preferences.
 *
 * @property darkMode A Boolean indicating whether dark mode is enabled. Default is false.
 * @property language A String representing the user's preferred language. Default is "en".
 * @property offline A Boolean indicating whether offline mode is enabled. Default is false.
 * @property location A UUID representing the user's preferred location. Default is null.
 * @property graphType A Boolean indicating the user's preferred graph type. Default is false.
 * @property lastUpdated A Long representing the last time the preferences were updated. Default is null.
 */
data class PrefModel(
    val darkMode: Boolean = false,
    val language: String = "en",
    val offline: Boolean = false,
    val location: UUID? = null,
    val graphType: Boolean = false,
    val lastUpdated: Long?,
)