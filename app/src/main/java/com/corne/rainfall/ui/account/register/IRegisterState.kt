package com.corne.rainfall.ui.account.register

import com.corne.rainfall.ui.account.login.ILoginState
import com.corne.rainfall.ui.base.form.FormItem
import com.corne.rainfall.ui.base.form.IKey
import com.corne.rainfall.ui.base.state.IBaseState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import javax.annotation.concurrent.Immutable

@GenerateMutableModel
@Immutable
interface IRegisterState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    val formValues: MutableMap<IKey, FormItem>
    val success: Boolean
    companion object {
        val initialState = IRegisterState(
            isLoading = false,
            error = null,
            formValues = mutableMapOf(),
            success = false
        )
    }
}
