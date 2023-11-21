package com.corne.rainfall.ui.location

import com.corne.rainfall.ui.base.form.FormItem
import com.corne.rainfall.ui.base.form.IKey
import com.corne.rainfall.ui.base.state.IBaseState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel

@GenerateMutableModel
interface AddLocationState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    val formValues: MutableMap<IKey, FormItem>

    companion object {
        val initialState = AddLocationState(
            isLoading = false,
            error = null,
            formValues = mutableMapOf(),
        )
    }
}
