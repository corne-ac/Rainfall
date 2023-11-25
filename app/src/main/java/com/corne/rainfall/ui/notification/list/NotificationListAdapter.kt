package com.corne.rainfall.ui.notification.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.corne.rainfall.data.model.AlertModel
import com.corne.rainfall.databinding.FragmentNotificationItemBinding

class NotificationListAdapter(
    private val values: List<AlertModel>,
) : RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {


    inner class ViewHolder(binding: FragmentNotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val body = binding.notificationBody
        val title = binding.notificationTitle
        val icon = binding.severityIcon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        FragmentNotificationItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.category
        holder.body.text = item.desc
        /*  if (item.category == "ABC") {
              holder.icon.setImageResource(R.drawable.)
          } else {
              holder.icon.setImageResource(R.drawable.)
          }*/

    }

}