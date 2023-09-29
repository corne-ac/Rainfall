package com.corne.rainfall.ui.settings

import com.corne.rainfall.ui.base.state.IBaseState
import javax.annotation.concurrent.Immutable

@Immutable
class SettingsState : IBaseState {
    var isLoading: Boolean = false
    var error: String? = null
}