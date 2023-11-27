package com.corne.rainfall.ui.warnings

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.corne.rainfall.databinding.FragmentNotificationListBinding
import com.corne.rainfall.databinding.FragmentWarningsListBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels

class WarningsListFragment :
    BaseStateFragment<FragmentWarningsListBinding, WarningsState, WarningsListViewModel>() {
    override val viewModel: WarningsListViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: WarningsState) {
        Log.d("TAG", "updateState: " + state.items.size)
        if (state.items.isNotEmpty()) {
            val adapter = WarningsListAdapter(state.items)
            binding.warningsList.adapter = adapter
        }

    }

    override suspend fun addContentToView() {
        binding.warningsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            requireContext(),
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })


        viewModel.getAllAlerts()

    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentWarningsListBinding =
        FragmentWarningsListBinding.inflate(inflater, container, false)


}