package com.corne.rainfall.data.conection

import kotlinx.coroutines.flow.Flow

interface IConnectedObserver {
    val connectionState: Flow<ConnectedState>

    val currentConnectionState: ConnectedState
}