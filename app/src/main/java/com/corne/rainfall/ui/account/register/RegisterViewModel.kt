package com.corne.rainfall.ui.account.register

import com.corne.rainfall.ui.account.login.ILoginState
import com.corne.rainfall.ui.account.login.MutableILoginState
import com.corne.rainfall.ui.account.login.mutable
import com.corne.rainfall.ui.base.state.BaseStateViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegisterViewModel @Inject constructor() : BaseStateViewModel<IRegisterState>(){
    private val stateStore = IRegisterState.initialState.mutable()
    override val state: StateFlow<IRegisterState> = stateStore.asStateFlow()


    private fun setState(update: MutableIRegisterState.() -> Unit) = stateStore.update(update)

}
