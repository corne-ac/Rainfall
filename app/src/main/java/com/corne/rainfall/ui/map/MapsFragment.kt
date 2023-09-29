package com.corne.rainfall.ui.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corne.rainfall.R
import com.corne.rainfall.data.FirmsRepository

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        val firmsRepository = FirmsRepository()
        //TODO: Show loading, can take long (Ryan)
        firmsRepository.fetchFirmsData(
            "8c668bf0c9bfbd24d9dfbaf06dcde7b3", //TODO: Move out of code, store safely
            "MODIS_NRT",
            "ZAF",
            1,
            "2023-09-29",
            { csvData ->
                val fireList: MutableList<FireLocationItem> = parseCsvData(csvData) //skip first (bool isfirst, skip), split commas, read lines seperately, list of objs w/ necessary fields (Try-catch if out of bounds)
                addMarkersToMap(fireList, googleMap)
            },
            { throwable ->
                //TODO: Handle error here
            }
        )

    }

    private fun addMarkersToMap(fireList: MutableList<FireLocationItem>, googleMap: GoogleMap) {
        for (fireLocationItem in fireList) {
            val latLng = LatLng(fireLocationItem.lat, fireLocationItem.long)
            googleMap.addMarker(MarkerOptions().position(latLng).title("Fire Location"))
        }
    }

    private fun parseCsvData(csvData: String): MutableList<FireLocationItem> { //Returns a mutable list of FireLocationItem Objects

        val lines = csvData.split("\n").toMutableList()
        lines.removeAt(0)

        val fireList = mutableListOf<FireLocationItem>()

        for (item in lines) {
            val attributes = item.split(",")

            if (attributes.size >= 15) {
                fireList.add(FireLocationItem(
                    attributes[1].toDouble(),
                    attributes[2].toDouble(),
                    attributes[7],
                    attributes[10].toInt(),
                    attributes[12].toDouble()
                ))
            }
        }
        return fireList
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}

class FireLocationItem (
    //Center of 1 km fire pixel, but not necessarily the actual location of the fire as one or more fires can be detected within the 1 km pixel.
    val lat: Double,
    val long: Double,
    val acqTime: String,
    val confidence: Int, //Confidence (0-100%)
    val frp: Double, //Fire Radiative Power (MW - megawatts) : Depicts the pixel-integrated fire radiative power in MW (megawatts).
)