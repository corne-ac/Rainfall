package com.corne.rainfall.ui.account.register

import com.corne.rainfall.ui.account.login.ILoginState
import com.corne.rainfall.ui.base.state.IBaseState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import javax.annotation.concurrent.Immutable

@GenerateMutableModel
@Immutable
interface IRegisterState : IBaseState {
    val isLoading: Boolean
    val error: Int?
    companion object {
        val initialState = IRegisterState(
            isLoading = false,
            error = null
        )
    }
}
