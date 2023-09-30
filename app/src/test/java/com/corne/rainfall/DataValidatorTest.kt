package com.corne.rainfall

import com.corne.rainfall.utils.DataValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class DataValidatorTest {
    @Test
    fun nullInput() {
        val errorMessage = DataValidator.dateValidation(null)
        assertEquals(null, errorMessage)
    }

    @Test
    fun emptyInput() {
        val errorMessage = DataValidator.dateValidation("")
        assertEquals(R.string.empty_date, errorMessage)
    }

    @Test
    fun nonEmptyInput() {
        val errorMessage = DataValidator.dateValidation("2023-09-30")
        assertEquals(null, errorMessage)
    }

}