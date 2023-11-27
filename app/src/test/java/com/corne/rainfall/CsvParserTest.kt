package com.corne.rainfall

import com.corne.rainfall.data.model.FireLocationItemModel
import com.corne.rainfall.utils.CsvParser
import com.corne.rainfall.utils.NetworkResult
import org.junit.Assert.assertEquals
import org.junit.Test

class CsvParserTest {
    @Test
    fun `parseCsvData returns empty list when csvData is empty`() {
        val csvData = ""
        val expected = emptyList<NetworkResult<FireLocationItemModel>>()

        val result = CsvParser.parseCsvData(csvData)

        assertEquals(expected, result)
    }
}