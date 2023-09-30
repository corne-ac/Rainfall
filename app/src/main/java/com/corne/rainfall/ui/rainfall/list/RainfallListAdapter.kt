package com.corne.rainfall.ui.rainfall.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.databinding.FragmentRainfallListItemBinding

class RainfallListAdapter(
    private val values: List<RainfallData>,
    private val onClickFunction: (RainfallData) -> Unit,
) : RecyclerView.Adapter<RainfallListAdapter.ViewHolder>() {


    inner class ViewHolder(binding: FragmentRainfallListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val heading: TextView = binding.headerTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        FragmentRainfallListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.heading.text = item.mm.toString()
        holder.heading.setOnClickListener { onClickFunction(item) }
    }

}