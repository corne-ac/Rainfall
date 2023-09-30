package com.corne.rainfall.ui.map

import com.corne.rainfall.data.model.FireLocationItemModel
import com.corne.rainfall.ui.base.state.IBaseState
import com.google.android.gms.maps.GoogleMap
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import javax.annotation.concurrent.Immutable

@GenerateMutableModel
@Immutable
interface IMapState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    val items: List<FireLocationItemModel>
    val googleMap: GoogleMap?

    companion object {
        val initialState = IMapState(
            isLoading = false,
            error = null,
            items = emptyList(),
            googleMap = null
        )
    }
}
