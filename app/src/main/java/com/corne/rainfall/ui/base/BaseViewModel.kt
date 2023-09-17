package com.corne.rainfall.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

/**
 * BaseViewModel is an abstract class intended to be used as a base class for ViewModels in our application.
 * It enforces the presence of a 'state' property, which represents the ViewModel's state as a StateFlow.
 * Subclasses must provide a concrete implementation of the 'state' property by specifying the type of state they use,
 * which should be a subtype of 'BaseState'.
 *
 * @param STATE The type of state this ViewModel deals with, expected to be a subtype of 'BaseState'.
 */
abstract class BaseViewModel<STATE : BaseState> : ViewModel() {

    /**
     * An abstract property that represents the state of the ViewModel as a StateFlow.
     * Subclasses must implement this property with their specific StateFlow.
     */
    abstract val state: StateFlow<STATE>

    /**
     * A read-only property that provides convenient access to the current state of the ViewModel.
     * It retrieves the most recent state by accessing the 'value' property of the 'state' property.
     */
    val currentState: STATE get() = state.value
}