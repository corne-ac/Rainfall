package com.corne.rainfall.data.model

/**
 * Data class representing information about a fire location.
 *
 * @property lat The latitude of the center of the 1 km fire pixel. Note that this may not necessarily
 * be the actual location of the fire, as one or more fires can be detected within the 1 km pixel.
 * @property long The longitude of the center of the 1 km fire pixel. Note that this may not necessarily
 * be the actual location of the fire, as one or more fires can be detected within the 1 km pixel.
 * @property acqTime The acquisition time of the fire data.
 * @property confidence The confidence level of the fire detection, represented as a percentage (0-100%).
 * @property frp The Fire Radiative Power (FRP) in megawatts (MW). It depicts the pixel-integrated fire
 * radiative power in MW (megawatts).
 */
data class FireLocationItemModel(
    val lat: Double,
    val long: Double,
    val acqTime: String,
    val confidence: Int,
    val frp: Double,
)