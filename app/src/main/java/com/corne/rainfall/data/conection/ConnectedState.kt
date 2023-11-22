package com.corne.rainfall.data.conection

sealed class ConnectedState {
    object Available : ConnectedState()
    object Unavailable : ConnectedState()
}