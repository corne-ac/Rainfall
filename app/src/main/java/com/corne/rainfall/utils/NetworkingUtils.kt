package com.corne.rainfall.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.corne.rainfall.data.conection.ConnectedState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged


val Context.connectivityManager
    get(): ConnectivityManager {
        return getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


fun ConnectivityManager.observeConnectivityAsFlow() = callbackFlow {
    trySend(currentConnectivityState)

    val callback = NetworkCallback { connectionState -> trySend(connectionState) }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    registerNetworkCallback(networkRequest, callback)

    awaitClose {
        unregisterNetworkCallback(callback)
    }
}.distinctUntilChanged()

val ConnectivityManager.currentConnectivityState: ConnectedState
    get() {

        val connected = allNetworks.any { network ->
            getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
        }

        return if (connected) ConnectedState.Available else ConnectedState.Unavailable
    }

fun NetworkCallback(callback: (ConnectedState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectedState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectedState.Unavailable)
        }

        override fun onUnavailable() {
            callback(ConnectedState.Unavailable)
        }
    }
}