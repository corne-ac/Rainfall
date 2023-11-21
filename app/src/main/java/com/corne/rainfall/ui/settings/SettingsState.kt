package com.corne.rainfall.ui.settings

import com.corne.rainfall.ui.base.state.IBaseState
import javax.annotation.concurrent.Immutable

/**
 * Immutable class representing the state of the Settings screen.
 *
 * This class implements the IBaseState interface and provides properties for tracking the loading state and any errors.
 *
 * @property isLoading A boolean indicating whether the Settings screen is currently loading data.
 * @property error A nullable string that contains an error message if an error occurred.
 */
@Immutable
class SettingsState : IBaseState {
    var isLoading: Boolean = false
    var error: String? = null
}