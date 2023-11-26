package com.corne.rainfall.data.conection

import android.net.ConnectivityManager
import com.corne.rainfall.utils.currentConnectivityState
import com.corne.rainfall.utils.observeConnectivityAsFlow
import kotlinx.coroutines.flow.Flow

/**
 * This class is an implementation of the IConnectedObserver interface.
 * It provides the functionality to observe the current connectivity state.
 */
class ConnectedObserverImpl(
    /**
     * The ConnectivityManager provides access to the system connectivity services.
     */
    private val connectivityManager: ConnectivityManager,
) : IConnectedObserver {

    /**
     * This property provides a Flow of the current connectivity state.
     * It uses the observeConnectivityAsFlow extension function on the ConnectivityManager.
     *
     * @return A Flow of ConnectedState.
     */
    override val connectionState: Flow<ConnectedState>
        get() = connectivityManager.observeConnectivityAsFlow()

    /**
     * This property provides the current connectivity state.
     * It uses the currentConnectivityState extension function on the ConnectivityManager.
     *
     * @return The current ConnectedState.
     */
    override val currentConnectionState: ConnectedState
        get() = connectivityManager.currentConnectivityState
}