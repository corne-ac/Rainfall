package com.corne.rainfall.ui.home

import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.ui.base.state.IBaseState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import java.util.UUID
import javax.annotation.concurrent.Immutable

@GenerateMutableModel
@Immutable
interface IHomeState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    val defaultLocation: UUID?
    val allLocationsList: List<LocationModel>

    companion object {
        val initialState = IHomeState(
            isLoading = false,
            error = null,
            defaultLocation = null,
            allLocationsList = emptyList()
        )
    }
}
