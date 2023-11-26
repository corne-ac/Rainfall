package com.corne.rainfall.ui.notification.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.corne.rainfall.data.model.AlertModel
import com.corne.rainfall.databinding.FragmentNotificationListBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels

class notificationListFragment :
    BaseStateFragment<FragmentNotificationListBinding, NotificationsState, NotificationListViewModel>() {
    override val viewModel: NotificationListViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: NotificationsState) {
        Log.d("TAG", "updateState: " + state.items.size)
        if (state.items.isNotEmpty()) {
            val adapter = NotificationListAdapter(state.items)
            binding.notificationList.adapter = adapter
        }

    }

    override suspend fun addContentToView() {
        binding.notificationList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            requireContext(),
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )

        viewModel.getAllAlerts()

        binding.clearBtn.setOnClickListener {
            val data = viewModel.state.value.items
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentNotificationListBinding =
        FragmentNotificationListBinding.inflate(inflater, container, false)


}