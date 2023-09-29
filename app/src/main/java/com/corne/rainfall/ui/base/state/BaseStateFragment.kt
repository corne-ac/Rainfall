package com.corne.rainfall.ui.base.state

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.corne.rainfall.ui.base.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A base class for Fragments in the application that use ViewBinding and ViewModel.
 *
 * @param VB The type of ViewBinding associated with this Fragment.
 * @param S The type of state managed by the associated ViewModel.
 * @param VM The type of ViewModel associated with this Fragment.
 */
abstract class BaseStateFragment<VB : ViewBinding, S : IBaseState, VM : BaseStateViewModel<S>> :
    BaseFragment<VB>() {

    /**
     * Provides access to the associated ViewModel. Subclasses must implement this property.
     */
    protected abstract val viewModel: VM

    /**
     * Watches the state changes in the ViewModel and updates the UI accordingly.
     */
    private fun watchState() {
        viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> updateState(state) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Start watching the ViewModel's state changes.
        watchState()
    }

    /**
     * This method is called when the ViewModel's state changes, and it should be implemented
     * to update the UI based on the new state.
     *
     * @param state The new state of the ViewModel.
     */
    abstract fun updateState(state: S)

    // TODO: add popup messages for errors, loading, info, offline, etc.
}