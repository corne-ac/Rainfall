package com.corne.rainfall.ui.account.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentHomeBinding
import com.corne.rainfall.databinding.FragmentRegisterBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.home.HomeViewModel
import com.corne.rainfall.ui.home.IHomeState

class RegisterFragment : BaseStateFragment<FragmentRegisterBinding, IRegisterState, RegisterViewModel>(){
    override val viewModel: RegisterViewModel
        get() = TODO("Not yet implemented")

    override fun updateState(state: IRegisterState) {
        TODO("Not yet implemented")
    }

    override suspend fun addContentToView() {
        TODO("Not yet implemented")
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}