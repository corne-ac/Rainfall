package com.corne.rainfall.utils

/**
 * Utility object containing constant values used throughout the application.
 */
object Constants {
    /**
     * The base URL for the FIRMS (Fire Information for Resource Management System) API.
     */
    const val FIRMS_BASE_URL = "https://firms.modaps.eosdis.nasa.gov/"

    /**
     * The name of the local room database used for storing rainfall data.
     */
    const val DATABASE_NAME = "rainfall_database_2.sqlite"

    /**
     * The base URL for the OpenWeatherMap API.
     */
    const val WEATHER_BASE_URL = "https://tile.openweathermap.org/"

    /**
     * The base URL for the Weather Alerts API.
     */
    const val WEATHER_ALERTS_BASE_URL = "http://api.weatherapi.com/v1/"

    const val MAP_TILE_SIZE_IN_DP = 256

    const val BACKUP_SUFFIX = "-bkp"
    const val WAL_FILE_SUFFIX = "-wal"
    const val SHM_FILE_SUFFIX = "-shm"


}