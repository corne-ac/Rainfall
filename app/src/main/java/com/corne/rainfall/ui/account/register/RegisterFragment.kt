package com.corne.rainfall.ui.account.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentHomeBinding
import com.corne.rainfall.databinding.FragmentRegisterBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import com.corne.rainfall.ui.home.HomeViewModel
import com.corne.rainfall.ui.home.IHomeState
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : BaseStateFragment<FragmentRegisterBinding, IRegisterState, RegisterViewModel>(){
    override val viewModel: RegisterViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: IRegisterState) {
        setUpFormState(state)
        binding.registerBtn.isEnabled = false
        binding.registerBtn.isEnabled = viewModel.isFormValid()
        if (state.success) {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override suspend fun addContentToView() {

        viewModel.setUpForm()

        binding.email.binding.value.doAfterTextChanged {
            viewModel.updateState(RegisterForm.EMAIL, it.toString())
        }

        binding.password.binding.value.doAfterTextChanged {
            viewModel.updateState(RegisterForm.PASSWORD, it.toString())
        }

        binding.confirmPassword.binding.value.doAfterTextChanged {
            viewModel.updateState(RegisterForm.CONFIRM_PASSWORD, it.toString())
        }

        binding.loginAction.setOnClickListener  {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerBtn.setOnClickListener {
            viewModel.registerUser()

        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)

    private fun setUpFormState(state: IRegisterState) {
        state.formValues.forEach {
            val value = it.value
            when (it.key) {
                RegisterForm.EMAIL -> showError(
                    value.errorMessage, binding.email.binding.valueInputLayout
                )

                RegisterForm.PASSWORD -> showError(
                    value.errorMessage, binding.password.binding.valueInputLayout
                )

                RegisterForm.CONFIRM_PASSWORD -> showError(
                    value.errorMessage, binding.confirmPassword.binding.valueInputLayout
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

}