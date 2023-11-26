package com.corne.rainfall.data.conection

/**
 * This is a sealed class representing the state of network connectivity.
 * It has two possible states: Available and Unavailable.
 */
sealed class ConnectedState {
    /**
     * This object represents the state when network connectivity is available.
     */
    data object Available : ConnectedState()

    /**
     * This object represents the state when network connectivity is unavailable.
     */
    data object Unavailable : ConnectedState()
}