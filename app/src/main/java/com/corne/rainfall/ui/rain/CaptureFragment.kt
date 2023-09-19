package com.corne.rainfall.ui.rain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.corne.rainfall.databinding.FragmentCaptureBinding
import com.corne.rainfall.ui.base.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels


class CaptureFragment :
    BaseStateFragment<FragmentCaptureBinding, CaptureState, CaptureViewModel>() {
    override val viewModel: CaptureViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: CaptureState) {



        binding.saveBtn.isEnabled = state.error == -1
        // TODO: show the loading indicator with -> state.isLoading
        showProgressLoader(state.isLoading)



    }

    fun showProgressLoader(show: Boolean) {
        if (show) {

        } else {

        }
    }

    override suspend fun addContentToView() {
        binding.dateInput.binding.value.doAfterTextChanged { viewModel.setDate(it.toString()) }
        binding.startTimeInput.binding.value.doAfterTextChanged { viewModel.setStartTime(it.toString()) }
        binding.endTimeInput.binding.value.doAfterTextChanged { viewModel.setEndTime(it.toString()) }
        binding.rainMmInput.binding.value.doAfterTextChanged { viewModel.setRainMm(it.toString()) }
        binding.saveBtn.setOnClickListener { viewModel.add() }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCaptureBinding = FragmentCaptureBinding.inflate(inflater, container, false)
}
