package com.corne.rainfall.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.corne.rainfall.ui.components.LoadingDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

/**
 *  A base class for Fragments in the application with no state associated with them.
 *
 * @param VB The type of ViewBinding associated with this Fragment.
 * */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    /**
     * A nullable property used to hold the ViewBinding associated with this Fragment.
     * It is initialized as null initially and will be set when the ViewBinding is created.
     */
    private var _binding: VB? = null

    /**
     * A read-only property that provides access to the non-null ViewBinding instance.
     * This property must only be accessed after the fragment has been initialized and before it is destroyed.
     */
    protected val binding get() = _binding!!

    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        // Inflate the ViewBinding and set the _binding property.
        _binding = createViewBinding(inflater, container)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            // Add the necessary UI components and content to the view.
            addContentToView()
        }

    }

    /**
     * This method should be implemented to add the necessary UI components and content to the view.
     */
    abstract suspend fun addContentToView()
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Creates the ViewBinding associated with this Fragment. Subclasses must implement this method.
     * General implementation would look like: override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentCustomBinding.inflate(inflater, container, false)
     * @param inflater The LayoutInflater to inflate the ViewBinding.
     * @param container The ViewGroup container for the ViewBinding.
     * @return The created ViewBinding instance.
     */

    protected abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    private val loadingDialog = LoadingDialog()

    // TODO: comments for these methods
    fun showProgress(isLoading: Boolean) {

    }

    fun showError(title: String, message: Int) {
        MaterialAlertDialogBuilder(requireContext()).setMessage(message).setTitle(title)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

}