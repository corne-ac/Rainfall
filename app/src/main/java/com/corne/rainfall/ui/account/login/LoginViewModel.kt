package com.corne.rainfall.ui.account.login

import com.corne.rainfall.ui.base.state.BaseStateViewModel
import com.corne.rainfall.ui.home.IHomeState
import com.corne.rainfall.ui.home.MutableIHomeState
import com.corne.rainfall.ui.home.mutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseStateViewModel<ILoginState>(){
    private val stateStore = ILoginState.initialState.mutable()
    override val state: StateFlow<ILoginState> = stateStore.asStateFlow()


    private fun setState(update: MutableILoginState.() -> Unit) = stateStore.update(update)

}