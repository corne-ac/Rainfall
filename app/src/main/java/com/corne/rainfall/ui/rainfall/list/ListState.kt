package com.corne.rainfall.ui.rainfall.list

import com.corne.rainfall.data.model.RainData
import com.corne.rainfall.ui.base.state.IBaseState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import javax.annotation.concurrent.Immutable

@GenerateMutableModel
@Immutable
interface ListState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    val items: List<RainData>

    companion object {
        val initialState = ListState(
            isLoading = false,
            error = null,
            items = emptyList()
        )
    }
}
