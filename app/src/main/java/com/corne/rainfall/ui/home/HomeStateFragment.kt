package com.corne.rainfall.ui.home

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


    override fun updateState(state: IHomeState) {


        if (state.isLoading) {
            return
        }


        // Sample data for the spinner
        if (state.allLocationsList.isNotEmpty()) {
            val adapter = ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item,
                state.allLocationsList.map { it.name })
            binding.locationsSpinner.adapter = adapter

            binding.locationsSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
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

            if (state.defaultLocation != null) {
                state.allLocationsList.find { it.locationUID == state.defaultLocation!! }?.let {
                    binding.locationsSpinner.setSelection(adapter.getPosition(it.name))
                }
            }
        }

    }

    override suspend fun addContentToView() {
        viewModel.loadUserLocationData()

        binding.rainfallCaptured.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_rainfallListFragment) }
        binding.notifications.setOnClickListener { findNavController().navigate(R.id.action_navigation_home_to_navigation_notifications_list) }
        binding.fireRisks.setOnClickListener { findNavController().navigate(R.id.action_navigation_home_to_navigation_notifications_list) }
        binding.weatherWarnings.setOnClickListener { }
        binding.addLocation.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_add_location)
        }


    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}
