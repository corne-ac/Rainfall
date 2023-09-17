package com.corne.rainfall.ui.rain

import com.corne.rainfall.ui.base.BaseState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import javax.annotation.concurrent.Immutable

/*
@Immutable
@GenerateMutableModel
interface CaptureState : BaseState {
    val isLoading: Boolean
    val error: String?

    companion object {
        val initialState = com.corne.rainfall.ui.rain.CaptureState(false, null)
    }
}*/
@GenerateMutableModel
@Immutable
interface CaptureState : BaseState {
    val isLoading: Boolean
    val error: String?

    companion object {
        val initialState = CaptureState(
            isLoading = false,
            error = null
        )
    }
}
