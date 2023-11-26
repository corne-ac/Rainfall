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

    fun emailConfirmation(it: String?): Int? {
        return when {
            it == null -> null
            isEmpty(it) -> R.string.empty_email
            else -> null
        }
    }

    fun passwordValidation(it: String?): Int? {
        return when {
            it == null -> null
            isEmpty(it) -> R.string.empty_password
            else -> null
        }
    }

    fun confirmPasswordValidation(it: String?, confirm: String): Int? {
        return when {
            it == null -> null
            isEmpty(it) -> R.string.empty_confirm_password
            it != confirm -> R.string.confirm_no_match
            else -> null
        }
    }

    fun startTimeValidation(it: String?): Int? {
        return when {
            it == null -> null
            isEmpty(it) -> R.string.empty_start_time
            else -> null
        }
    }

    fun endTimeValidation(it: String?): Int? {
        return when {
            it == null -> null
            isEmpty(it) -> R.string.empty_end_time
            else -> null
        }
    }


}