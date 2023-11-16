package com.corne.rainfall.ui.graph

import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.ui.base.state.IBaseState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import javax.annotation.concurrent.Immutable

@GenerateMutableModel
@Immutable
interface IGraphState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    val isGraphBar: Boolean
    val rainDataList: List<RainfallData>


    companion object {
        val initialState = IGraphState(
            isLoading = false,
            error = null,
            isGraphBar = false,
            rainDataList = emptyList()
        )
    }
}