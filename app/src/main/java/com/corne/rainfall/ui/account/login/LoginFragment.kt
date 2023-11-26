package com.corne.rainfall.ui.account.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentLoginBinding
import com.corne.rainfall.ui.account.register.IRegisterState
import com.corne.rainfall.ui.account.register.RegisterForm
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : BaseStateFragment<FragmentLoginBinding, ILoginState, LoginViewModel>(){
    override val viewModel: LoginViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: ILoginState) {
        setUpFormState(state)
        binding.loginBtn.isEnabled = false
        binding.loginBtn.isEnabled = viewModel.isFormValid()
        if (state.success) {
            findNavController().navigate(R.id.action_loginFragment_to_navigation_settings)
        }

        if (state.error != null) {
            MaterialAlertDialogBuilder(requireContext()).setMessage(state.error.toString()).setTitle("Error")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
    }

    override suspend fun addContentToView() {

        viewModel.setUpForm()

        binding.email.binding.value.doAfterTextChanged {
            viewModel.updateState(LoginForm.EMAIL, it.toString())
        }

        binding.password.binding.value.doAfterTextChanged {
            viewModel.updateState(LoginForm.PASSWORD, it.toString())
        }

        binding.loginBtn.setOnClickListener {
            viewModel.signIn()
        }

        binding.registerAction.setOnClickListener  {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    private fun setUpFormState(state: ILoginState) {
        state.formValues.forEach {
            val value = it.value
            when (it.key) {
                LoginForm.EMAIL -> showError(
                    value.errorMessage, binding.email.binding.valueInputLayout
                )

                LoginForm.PASSWORD -> showError(
                    value.errorMessage, binding.password.binding.valueInputLayout
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