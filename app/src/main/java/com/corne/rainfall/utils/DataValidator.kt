package com.corne.rainfall.utils

import com.corne.rainfall.R

object DataValidator {
    private fun isEmpty(input: String): Boolean {
        return input.isEmpty()
    }

    fun dateValidation(it: String?): Int? {
        return when {
            it == null -> null
            isEmpty(it) -> R.string.empty_date
            else -> null
        }
    }

}