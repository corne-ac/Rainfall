package com.corne.rainfall.utils

import com.corne.rainfall.R

/**
 * Utility object for data validation operations.
 */
object DataValidator {
    /**
     * Checks if the input string is empty.
     *
     * @param input The string to check for emptiness.
     * @return `true` if the input string is empty, `false` otherwise.
     */
    private fun isEmpty(input: String): Boolean {
        return input.isEmpty()
    }

    /**
     * Validates a date string.
     *
     * @param it The date string to validate.
     * @return An integer resource ID representing a validation error message, or `null` if the date is valid.
     */
    fun dateValidation(it: String?): Int? {
        return when {
            it == null -> null
            isEmpty(it) -> R.string.empty_date
            else -> null
        }
    }

}