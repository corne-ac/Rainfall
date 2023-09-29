package com.corne.rainfall.ui.rainfall.capture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.corne.rainfall.databinding.FragmentCaptureBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import com.google.android.material.textfield.TextInputLayout


class CaptureFragment :
    BaseStateFragment<FragmentCaptureBinding, CaptureState, CaptureViewModel>() {
    override val viewModel: CaptureViewModel by hiltMainNavGraphViewModels()
    private var count = 0

    override fun updateState(state: CaptureState) {
        count++
        binding.saveBtn.text = count.toString()
        println("We have set the state to loading$count")

        binding.saveBtn.isEnabled = viewModel.isFormValid()

        state.formValues.forEach {
            val value = it.value
            when (it.key) {
                CaptureForm.DATE -> showError(
                    value.errorMessage, binding.dateInput.binding.valueInputLayout
                )

                CaptureForm.START_TIME -> showError(
                    value.errorMessage, binding.startTimeInput.binding.valueInputLayout
                )

                CaptureForm.END_TIME -> showError(
                    value.errorMessage, binding.endTimeInput.binding.valueInputLayout
                )

                CaptureForm.RAIN_MM -> showError(
                    value.errorMessage, binding.rainMmInput.binding.valueInputLayout
                )
            }
        }
    }

    private fun showError(errorMessage: Int?, valueInputLayout: TextInputLayout) {
        errorMessage?.let { errorMsg ->
            valueInputLayout.error = requireContext().getString(errorMsg)
        } ?: run {
            valueInputLayout.error = null
        }
    }


    fun showProgressLoader(show: Boolean) {
        if (show) {

        } else {

        }
    }

    override suspend fun addContentToView() {
        viewModel.setUpForm()

        binding.dateInput.binding.value.doAfterTextChanged {
            viewModel.updateState(CaptureForm.DATE, it.toString())
        }
        binding.startTimeInput.binding.value.doAfterTextChanged {
            viewModel.updateState(CaptureForm.START_TIME, it.toString())
        }
        binding.endTimeInput.binding.value.doAfterTextChanged {
            viewModel.updateState(CaptureForm.END_TIME, it.toString())
        }
        binding.rainMmInput.binding.value.doAfterTextChanged {
            viewModel.updateState(CaptureForm.RAIN_MM, it.toString())
        }
        binding.saveBtn.setOnClickListener { viewModel.add() }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCaptureBinding = FragmentCaptureBinding.inflate(inflater, container, false)
}
