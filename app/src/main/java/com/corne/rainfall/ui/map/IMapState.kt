package com.corne.rainfall.ui.map

import com.corne.rainfall.data.model.FireLocationItemModel
import com.corne.rainfall.ui.base.state.IBaseState
import com.corne.rainfall.utils.NetworkResult
import com.google.android.gms.maps.GoogleMap
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import javax.annotation.concurrent.Immutable

@GenerateMutableModel
@Immutable
interface IMapState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    val items: List<NetworkResult<FireLocationItemModel>>
    val googleMap: GoogleMap?
    val isDarkMode: Boolean
    val isOfflinePresence: Boolean?
    val isConnected: Boolean?

    companion object {
        val initialState = IMapState(
            isLoading = false,
            error = null,
            items = emptyList(),
            googleMap = null,
            isDarkMode = false,
            isOfflinePresence = null,
            isConnected = null
        )
    }
}
