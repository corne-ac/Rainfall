package com.corne.rainfall.ui.account.login

import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.ui.base.form.FormItem
import com.corne.rainfall.ui.base.form.IKey
import com.corne.rainfall.ui.base.state.IBaseState
import com.corne.rainfall.ui.home.IHomeState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import java.util.UUID
import javax.annotation.concurrent.Immutable

@GenerateMutableModel
@Immutable
interface ILoginState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    val formValues: MutableMap<IKey, FormItem>
    val success: Boolean

    companion object {
        val initialState = ILoginState(
            isLoading = false,
            error = null,
            formValues = mutableMapOf(),
            success = false
        )
    }
}