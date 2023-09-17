package com.corne.rainfall.ui.base

import dev.shreyaspatil.mutekt.core.MutektMutableState
import kotlinx.coroutines.flow.StateFlow

class StateStore<STATE, MUTABLE_STATE : MutektMutableState<STATE, out STATE>>(
    initialState: MUTABLE_STATE,
) {
    private val mutableState = initialState
    val state: StateFlow<STATE> = mutableState.asStateFlow()
  /*  inline fun <reified T : STATE> setState(update: T.() -> Unit) {
        (mutableState as T).update()
    }*/

    fun setState(updateFun: (MUTABLE_STATE) -> Unit) {
        updateFun(mutableState)
    }


}