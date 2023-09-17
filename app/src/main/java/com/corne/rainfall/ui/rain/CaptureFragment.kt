package com.corne.rainfall.ui.rain

import android.view.LayoutInflater
import android.view.ViewGroup
import com.corne.rainfall.databinding.FragmentCaptureBinding
import com.corne.rainfall.ui.base.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels


class CaptureFragment :
    BaseStateFragment<FragmentCaptureBinding, CaptureState, CaptureViewModel>() {
    override val viewModel: CaptureViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: CaptureState) {
        binding.saveBtn.isEnabled = state.error == ""
        // TODO: show the loading indicator with -> state.isLoading
        showProgress(true)

        val errorMessage = state.error
        if (errorMessage != null) {
            //TODO: show error message.
            showError("Failed to add rain item", errorMessage)
        }
    }

    override suspend fun addContentToView() {
        viewModel.add()
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCaptureBinding = FragmentCaptureBinding.inflate(inflater, container, false)
}
