package com.corne.rainfall.ui.rainfall.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.corne.rainfall.R
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.databinding.FragmentRainfallListBinding
import com.corne.rainfall.databinding.FragmentRainfallListItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class RainfallListAdapter(
    private val values: List<RainfallData>,
    private val binding: FragmentRainfallListBinding,
    private val minMax: MinMax,
    private val onClickFunction: (RainfallData) -> Unit,
) : RecyclerView.Adapter<RainfallListAdapter.ViewHolder>() {


    inner class ViewHolder(binding: FragmentRainfallListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val amount: TextView = binding.rainfallAmm
        val dateTime: TextView = binding.DateTime
        val note: TextView = binding.note
        val icon: ImageView = binding.icon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        FragmentRainfallListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.amount.text = item.mm.toString() + " mm"

        // Check if item mm is in lower third, middle third, or higher third of the minMax range
        if (minMax.max > minMax.min) {
            val range = minMax.max - minMax.min
            val lowerThird = minMax.min + range / 3
            val upperThird = minMax.min + 2 * range / 3

            // Set different icons based on the position in the minMax range
            when {
                item.mm < lowerThird -> holder.icon.setImageResource(R.drawable.cloud_24)
                item.mm < upperThird -> holder.icon.setImageResource(R.drawable.cloud_rain_24)
                else -> holder.icon.setImageResource(R.drawable.cloud_thunder_24)
            }
        } else {
            // If the min and max are exactly the same, default to one spot
            holder.icon.setImageResource(R.drawable.cloud_24)
        }

        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormatter.format(item.date)

        holder.dateTime.text = "${formattedDate}  ${item.startTime} -> ${item.endTime}"
        holder.note.text = "Note: ${item.notes}" //TODO: Extract strings, giving issues with context
//        holder.heading.setOnClickListener { onClickFunction(item) }
//        binding.rainfallList.adapter?.notifyDataSetChanged()
    }

}