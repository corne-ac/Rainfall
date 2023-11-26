package com.corne.rainfall.data.conection

import kotlinx.coroutines.flow.Flow

/**
 * This is an interface for observing the state of network connectivity.
 * It provides two properties: connectionState and currentConnectionState.
 */
interface IConnectedObserver {
    /**
     * This property provides a Flow of the current connectivity state.
     * It can be used to observe changes in the connectivity state.
     *
     * @return A Flow of ConnectedState.
     */
    val connectionState: Flow<ConnectedState>

    /**
     * This property provides the current connectivity state.
     * It can be used to get the current state of the network connectivity.
     *
     * @return The current ConnectedState.
     */
    val currentConnectionState: ConnectedState
}