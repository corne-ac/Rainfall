package com.corne.rainfall.ui.rainfall.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.corne.rainfall.data.model.RainfallData
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

        if (state.items.isEmpty()) binding.empty.visibility = View.VISIBLE
        else binding.empty.visibility = View.GONE

        var minMax: MinMax = getMinMax(state.items)

        binding.rainfallList.adapter = RainfallListAdapter(state.items, binding, minMax) {
            //TODO: send to view
        }
    }

    private fun getMinMax(items: List<RainfallData>): MinMax {
        val minMax = MinMax()
        if (items.isEmpty()) {
            return minMax
        }
        minMax.min = items[0].mm.toInt()
        minMax.max = items[0].mm.toInt()
        //Iterate through the list
        for (item in items) {
            val rainAmount = item.mm.toInt()

            if (rainAmount < minMax.min)
                minMax.min = rainAmount

            if (rainAmount > minMax.max)
                minMax.max = rainAmount
        }
        return minMax
    }


    override suspend fun addContentToView() {
        binding.rainfallList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            requireContext(),
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )

        viewModel.getRainfallData()


    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRainfallListBinding = FragmentRainfallListBinding.inflate(inflater, container, false)
}

class MinMax {
    var min: Int = 0
    var max: Int = 0
}