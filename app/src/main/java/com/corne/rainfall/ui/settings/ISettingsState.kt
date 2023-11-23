package com.corne.rainfall.ui.settings

import com.corne.rainfall.ui.base.state.IBaseState
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel
import javax.annotation.concurrent.Immutable

/**
 * Immutable class representing the state of the Settings screen.
 *
 * This class implements the IBaseState interface and provides properties for tracking the loading state and any errors.
 *
 * @property isLoading A boolean indicating whether the Settings screen is currently loading data.
 * @property error A nullable string that contains an error message if an error occurred.
 */
@GenerateMutableModel
@Immutable
interface ISettingsState : IBaseState {
    val isLoading: Boolean
    val error: String?

    companion object {
        val initialState = ISettingsState(
            isLoading = false,
            error = null,
        )
    }

}