package com.corne.rainfall.utils

import com.corne.rainfall.R
import com.corne.rainfall.data.model.FireLocationItemModel

/**
 * Utility object for parsing CSV files into a list of NetworkResult objects.
 * Each NetworkResult object contains a FireLocationItemModel object if the CSV line could be parsed successfully.
 * Otherwise, it contains an error.
 */
object CsvParser {
    /**
     * Parses CSV data into a list of NetworkResult objects.
     * Each NetworkResult object contains a FireLocationItemModel object if the CSV line could be parsed successfully.
     * Otherwise, it contains an error.
     *
     * @param csvData The CSV data to be parsed.
     * @return A list of NetworkResult objects.
     */
    fun parseCsvData(csvData: String): List<NetworkResult<FireLocationItemModel>> {
        return csvData.split("\n").drop(1).map { networkResult(it.split(",")) }
    }

    /**
     * Converts a list of CSV attributes into a NetworkResult object.
     * If the list has the correct size, a NetworkResult object containing a FireLocationItemModel object is returned.
     * Otherwise, a NetworkResult object containing an error is returned.
     *
     * @param attributes The list of CSV attributes.
     * @return A NetworkResult object.
     */
    private fun networkResult(attributes: List<String>) =
        if (attributes.size == 15) {
            NetworkResult.success(
                FireLocationItemModel(
                    attributes[1].toDouble(), // Latitude
                    attributes[2].toDouble(), // Longitude
                    attributes[7], // Fire name
                    attributes[10].toInt(), // Fire size
                    attributes[12].toDouble() // Fire intensity
                )
            )
        } else NetworkResult.error(R.string.error_fire_parse)
}