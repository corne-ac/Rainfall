package com.corne.rainfall.ui.rainfall.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.corne.rainfall.databinding.FragmentRainfallListBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels


class ListFragment :
    BaseStateFragment<FragmentRainfallListBinding, ListState, ListViewModel>() {
    override val viewModel: ListViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: ListState) {


//        binding.saveBtn.isEnabled = state.error == -1
        // TODO: show the loading indicator with -> state.isLoading
        showProgressLoader(state.isLoading)


    }

    fun showProgressLoader(show: Boolean) {
        if (show) {

        } else {

        }
    }

    override suspend fun addContentToView() {
        viewModel.getRainfallData()
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRainfallListBinding = FragmentRainfallListBinding.inflate(inflater, container, false)
}
