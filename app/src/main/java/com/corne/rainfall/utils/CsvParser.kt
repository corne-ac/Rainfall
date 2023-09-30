package com.corne.rainfall.utils

import com.corne.rainfall.data.model.FireLocationItemModel


object CsvParser {
    fun parseCsvData(csvData: String): MutableList<FireLocationItemModel> { //Returns a mutable list of FireLocationItem Objects
        // TODO: error for if we cant parse the csv data Make network result error
        val lines = csvData.split("\n").toMutableList()
        lines.removeAt(0)

        val fireList = mutableListOf<FireLocationItemModel>()

        for (item in lines) {
            val attributes = item.split(",")

            if (attributes.size >= 15) {
                fireList.add(
                    FireLocationItemModel(
                        attributes[1].toDouble(),
                        attributes[2].toDouble(),
                        attributes[7],
                        attributes[10].toInt(),
                        attributes[12].toDouble()
                    )
                )
            }
        }
        return fireList
    }
}