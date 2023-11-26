package com.corne.rainfall.ui.account.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentLoginBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment

class LoginFragment : BaseStateFragment<FragmentLoginBinding, ILoginState, LoginViewModel>(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val viewModel: LoginViewModel
        get() = TODO("Not yet implemented")

    override fun updateState(state: ILoginState) {
        TODO("Not yet implemented")
    }

    override suspend fun addContentToView() {
        TODO("Not yet implemented")
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        TODO("Not yet implemented")
    }
}