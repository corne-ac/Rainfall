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
        // TODO: show the loading indicator with -> state.isLoading

        state.error?.let {

        }
        // TODO: show the loading
        state.isLoading

        if (state.items.isEmpty()) {
            // Show the message
        }

        binding.rainfallList.adapter = RainfallListAdapter(state.items) {
            println(it)
        }
    }


    override suspend fun addContentToView() {
     /*   binding.rainfallList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            requireContext(),
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
*/
        viewModel.getRainfallData()


    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRainfallListBinding = FragmentRainfallListBinding.inflate(inflater, container, false)
}
