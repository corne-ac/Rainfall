package com.corne.rainfall.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentHomeBinding
import com.corne.rainfall.ui.base.BaseFragment


class HomeStateFragment : BaseFragment<FragmentHomeBinding>() {
    override suspend fun addContentToView() {


        binding.rainfallCaptured.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_rainfallListFragment) }
        binding.notifications.setOnClickListener { findNavController().navigate(R.id.action_navigation_home_to_navigation_notifications_list) }
        binding.fireRisks.setOnClickListener { findNavController().navigate(R.id.action_navigation_home_to_navigation_notifications_list) }
        binding.weatherWarnings.setOnClickListener { }


        // Sample data for the spinner
        val data = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

        // Create an ArrayAdapter using the string array and a default spinner layout
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)

        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
//        binding.locationsSpinner.adapter = adapter
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}
