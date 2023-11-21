package com.corne.rainfall.ui.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.corne.rainfall.databinding.FragmentAddLocationBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import com.google.android.material.textfield.TextInputLayout


class AddLocationFragment :
    BaseStateFragment<FragmentAddLocationBinding, AddLocationState, AddLocationViewModel>() {
    override val viewModel: AddLocationViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: AddLocationState) {
        binding.saveBtn.isEnabled = viewModel.isFormValid()
        state.formValues.forEach {
            val value = it.value
            when (it.key) {
                ILocationForm.NAME -> showError(
                    value.errorMessage, binding.locationName.binding.valueInputLayout
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

        binding.locationName.binding.value.doAfterTextChanged {
            viewModel.updateState(ILocationForm.NAME, it.toString())
        }
        binding.saveBtn.setOnClickListener { viewModel.add() }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentAddLocationBinding = FragmentAddLocationBinding.inflate(inflater, container, false)
}
