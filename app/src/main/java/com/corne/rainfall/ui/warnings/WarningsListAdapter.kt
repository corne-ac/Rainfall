package com.corne.rainfall.ui.warnings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.corne.rainfall.data.model.AlertModel
import com.corne.rainfall.databinding.FragmentWarningsItemBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WarningsListAdapter(
    private val values: List<AlertModel>,
) : RecyclerView.Adapter<WarningsListAdapter.ViewHolder>() {


    inner class ViewHolder(binding: FragmentWarningsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val body = binding.warningBody
        val title = binding.warningTitle
        val icon = binding.severityIcon
        val location = binding.location
        val timeDuration = binding.time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        FragmentWarningsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.category
        holder.body.text = item.desc
        holder.location.text = item.location
        holder.timeDuration.text = formatAndCombineDates(item.effective!!, item.expires!!)
        /*  if (item.category == "ABC") {
              holder.icon.setImageResource(R.drawable.)
          } else {
              holder.icon.setImageResource(R.drawable.)
          }*/

    }

    private fun formatAndCombineDates(effective: String?, expires: String?): String? {

        if (effective.isNullOrEmpty() || expires.isNullOrEmpty()) return ""

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            val effectiveDate: Date = inputFormat.parse(effective)
            val expiresDate: Date = inputFormat.parse(expires)
            val formattedEffective: String = outputFormat.format(effectiveDate)
            val formattedExpires: String = outputFormat.format(expiresDate)
            "$formattedEffective -> $formattedExpires"
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

}