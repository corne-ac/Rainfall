package com.corne.rainfall.ui.settings

import com.corne.rainfall.ui.base.BaseState
import javax.annotation.concurrent.Immutable

@Immutable
class SettingsState : BaseState {
    var isLoading: Boolean = false
    var error: String? = null
}