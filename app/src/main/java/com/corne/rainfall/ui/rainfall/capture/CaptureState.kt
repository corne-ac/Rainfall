package com.corne.rainfall.ui.rainfall.capture

import com.corne.rainfall.ui.base.form.FormItem
import com.corne.rainfall.ui.base.form.IKey
import com.corne.rainfall.ui.base.state.IBaseState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel

@GenerateMutableModel
interface CaptureState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    val formValues: MutableMap<IKey, FormItem>

    companion object {
        val initialState = CaptureState(
            isLoading = false,
            error = null,
            formValues = mutableMapOf(),
        )
    }
}
