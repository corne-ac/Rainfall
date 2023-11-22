package com.corne.rainfall.data.conection

import android.net.ConnectivityManager
import com.corne.rainfall.utils.currentConnectivityState
import com.corne.rainfall.utils.observeConnectivityAsFlow
import kotlinx.coroutines.flow.Flow


// TODO: may need this ?? @OptIn(ExperimentalCoroutinesApi::class)
class ConnectedObserverImpl(
    private val connectivityManager: ConnectivityManager,
) : IConnectedObserver {
    override val connectionState: Flow<ConnectedState>
        get() = connectivityManager.observeConnectivityAsFlow()

    override val currentConnectionState: ConnectedState
        get() = connectivityManager.currentConnectivityState

}