package com.corne.rainfall.utils

open class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val message: String) : NetworkResult<T>()

    inline fun onSuccess(block: (T) -> Unit): NetworkResult<T> = apply {
        if (this is Success) {
            block(data)
        }
    }

    inline fun onError(block: (String) -> Unit): NetworkResult<T> = apply {
        if (this is Error) {
            block(message)
        }
    }

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String) = Error<T>(message)
    }


}