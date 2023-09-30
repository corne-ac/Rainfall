package com.corne.rainfall.utils

/**
 * A sealed class representing the result of a network operation, which can be either a success or an error.
 *
 * @param T The type of data that can be encapsulated in the result.
 */
sealed class NetworkResult<T> {
    /**
     * Represents a successful network operation result containing the data.
     *
     * @property data The data resulting from a successful operation.
     */
    data class Success<T>(val data: T) : NetworkResult<T>()

    /**
     * Represents an error that occurred during a network operation, along with an error message.
     *
     * @property message An integer representing the I18N translation message for error.
     */
    data class Error<T>(val message: Int) : NetworkResult<T>()


    /**
     * Execute the given [successMethod] only if this result is a success.
     *
     * @param successMethod The lambda function to execute if the result is a success.
     * @return The original [NetworkResult] instance.
     */
    inline fun onSuccess(successMethod: (T) -> Unit): NetworkResult<T> = apply {
        if (this is Success) {
            successMethod(data)
        }
    }

    /**
     * Execute the given [errorMethod] only if this result is a success.
     *
     * @param errorMethod The lambda function to execute if the result is a success.
     * @return The original [NetworkResult] instance.
     */
    inline fun onError(errorMethod: (Int) -> Unit): NetworkResult<T> = apply {
        if (this is Error) {
            errorMethod(message)
        }
    }

    /**
     * Utility object for creating a [Success] or an [Error] [NetworkResult].
     */
    companion object {
        /**
         * Create a [Success] result with the specified data.
         *
         * @param data The data to encapsulate in the [Success] result.
         * @return A [Success] instance containing the provided data.
         */
        fun <T> success(data: T) = Success(data)

        /**
         * Create an [Error] result with the specified error message.
         *
         * @param message An integer code representing the error message or status.
         * @return An [Error] instance with the provided error message.
         */
        fun <T> error(message: Int) = Error<T>(message)
    }


}