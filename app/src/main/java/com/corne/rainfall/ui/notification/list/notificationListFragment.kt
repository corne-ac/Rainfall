package com.corne.rainfall.ui.notification.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentHomeBinding
import com.corne.rainfall.databinding.FragmentNotificationItemBinding
import com.corne.rainfall.databinding.FragmentNotificationListBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import com.corne.rainfall.ui.home.HomeViewModel
import com.corne.rainfall.ui.home.IHomeState

class notificationListFragment : BaseStateFragment<FragmentNotificationListBinding, NotificationsState, NotificationListViewModel>() {
    override val viewModel: NotificationListViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: NotificationsState) {
    }

    override suspend fun addContentToView() {
        viewModel.getAlerts()

        binding.clearBtn.setOnClickListener {

            val data = viewModel.state.value.items

        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationListBinding = FragmentNotificationListBinding.inflate(inflater, container, false)


}