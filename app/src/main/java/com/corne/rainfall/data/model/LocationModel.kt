package com.corne.rainfall.data.model

import java.util.UUID

/**
 * This data class represents a Location model.
 * It contains two properties: locationUID and name.
 *
 * @property locationUID A unique identifier for the location. It is of type UUID.
 * @property name The name of the location. It is a String.
 */
data class LocationModel(
    val locationUID: UUID,
    val name: String,
)