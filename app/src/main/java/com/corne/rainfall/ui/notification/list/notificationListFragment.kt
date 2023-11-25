package com.corne.rainfall.ui.notification.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.corne.rainfall.databinding.FragmentNotificationListBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels

class notificationListFragment :
    BaseStateFragment<FragmentNotificationListBinding, NotificationsState, NotificationListViewModel>() {
    override val viewModel: NotificationListViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: NotificationsState) {
        if (state.items.isNotEmpty()) {
            val adapter = NotificationListAdapter(state.items)
            binding.notificationList.adapter = adapter
        }

    }

    override suspend fun addContentToView() {
        viewModel.getAlerts()

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