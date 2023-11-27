package com.corne.rainfall.ui.location

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
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

        viewModel.setOnSuccessCallback {
            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Successfully added Location!", Toast.LENGTH_SHORT).show()
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

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

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
