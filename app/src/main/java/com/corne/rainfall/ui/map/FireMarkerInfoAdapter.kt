package com.corne.rainfall.ui.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.corne.rainfall.R
import com.corne.rainfall.data.model.FireLocationItemModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import java.text.SimpleDateFormat
import java.util.Locale

class FireMarkerInfoAdapter ( private val context: Context) : GoogleMap.InfoWindowAdapter {
    //Set label contents appropriately
    override fun getInfoContents(marker: Marker): View? {
        val fireItem = marker.tag as? FireLocationItemModel ?: return null
        //Format time for display
        val time = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(
            SimpleDateFormat("HHmm", Locale.getDefault())
                .parse(fireItem.acqTime)!!
        )
        val view = LayoutInflater.from(context).inflate(
            R.layout.fire_marker_contents, null)
            view.findViewById<TextView>(
                R.id.acqTime
            ).text = "Acquired Time: ${time}"
            view.findViewById<TextView>(
                R.id.confidence
            ).text = "Confidence: ${fireItem.confidence}%"
        return view
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}
