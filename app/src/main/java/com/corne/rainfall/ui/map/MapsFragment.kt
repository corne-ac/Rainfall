package com.corne.rainfall.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.corne.rainfall.BuildConfig
import com.corne.rainfall.data.model.FireLocationItemModel
import com.corne.rainfall.databinding.FragmentMapsBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : BaseStateFragment<FragmentMapsBinding, IMapState, MapViewModel>() {
    override val viewModel: MapViewModel by hiltMainNavGraphViewModels()
    private lateinit var googleMap: SupportMapFragment

    private val callback = OnMapReadyCallback { googleMap ->

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        viewModel.getFireData(
            BuildConfig.FIRE_API_KEY, "MODIS_NRT", "ZAF", 1, "2023-09-29", googleMap
        )
    }

    private fun addMarkersToMap(
        fireList: MutableList<FireLocationItemModel>,
        googleMap: GoogleMap,
    ) {
        for (fireLocationItem in fireList) {
            val latLng = LatLng(fireLocationItem.lat, fireLocationItem.long)
            googleMap.addMarker(MarkerOptions().position(latLng).title("Fire Location"))
        }
    }

    override suspend fun addContentToView() {
        googleMap = binding.map.getFragment()
        googleMap.getMapAsync(callback)
    }

    override fun updateState(state: IMapState) {
        binding.progressBar.isVisible = state.isLoading

        state.googleMap?.let {
            addMarkersToMap(state.items.toMutableList(), it)
        }
    }


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMapsBinding = FragmentMapsBinding.inflate(inflater, container, false)
}