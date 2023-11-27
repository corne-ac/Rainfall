package com.corne.rainfall.ui.home

import LocationListItemAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentHomeBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels


class HomeStateFragment : BaseStateFragment<FragmentHomeBinding, IHomeState, HomeViewModel>() {
    override val viewModel: HomeViewModel by hiltMainNavGraphViewModels()
    private var firstRun = true

    override fun updateState(state: IHomeState) {


        if (state.isLoading) {
            return
        }


        // Sample data for the spinner
        if (state.allLocationsList.isNotEmpty()) {

            val adapter = LocationListItemAdapter(requireContext(), state.allLocationsList.map { it.name })

            binding.locationsSpinner.adapter = adapter

            fun onItemSelectedListener() = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    viewModel.saveDefaultLocation(state.allLocationsList[position].locationUID)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

            binding.locationsSpinner.onItemSelectedListener = onItemSelectedListener()

            if (state.defaultLocation != null) {
                state.allLocationsList.find { it.locationUID == state.defaultLocation!! }?.let {
                    binding.locationsSpinner.onItemSelectedListener = null
                    var add = 1
                    if (firstRun) {
                        add = 0
                        firstRun = false
                    }
                    binding.locationsSpinner.setSelection(adapter.getPosition(it.name) + add)
                    binding.locationsSpinner.onItemSelectedListener = onItemSelectedListener()

                }
            }
        }

    }

    override suspend fun addContentToView() {
        firstRun = true
        viewModel.loadUserLocationData()

        binding.rainfallCaptured.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_rainfallListFragment) }
        binding.notifications.setOnClickListener { findNavController().navigate(R.id.action_navigation_home_to_navigation_notifications_list) }
        binding.fireRisks.setOnClickListener { findNavController().navigate(R.id.action_navigation_home_to_navigation_fire_risk_info) }
        binding.weatherWarnings.setOnClickListener { findNavController().navigate(R.id.action_navigation_home_to_warningsListFragment) }
        binding.addLocation.setOnClickListener { findNavController().navigate(R.id.action_navigation_home_to_navigation_add_location) }
        binding.helpButton.setOnClickListener { findNavController().navigate(R.id.action_navigation_home_to_navigation_help) }


    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}

data class LocationItem(val name: String)
