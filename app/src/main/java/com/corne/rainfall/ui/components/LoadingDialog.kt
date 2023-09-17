package com.corne.rainfall.ui.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.corne.rainfall.databinding.DialogLoadingBinding


class LoadingDialog : DialogFragment() {
    /**
     * A nullable property used to hold the ViewBinding associated with this DialogFragment.
     * It is initialized as null initially and will be set when the ViewBinding is created.
     */
    private var _binding: DialogLoadingBinding? = null

    /**
     * A read-only property that provides access to the non-null ViewBinding instance.
     * This property must only be accessed after the fragment has been initialized and before it is destroyed.
     */
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }*/

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}