package com.corne.rainfall.ui.map

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.corne.rainfall.BuildConfig
import com.corne.rainfall.R
import com.corne.rainfall.data.model.FireLocationItemModel
import com.corne.rainfall.databinding.FragmentMapsBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import com.corne.rainfall.utils.BitmapHelper
import com.corne.rainfall.utils.NetworkResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.TileOverlayOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MapsFragment : BaseStateFragment<FragmentMapsBinding, IMapState, MapViewModel>() {

    override val viewModel: MapViewModel by hiltMainNavGraphViewModels()
    private lateinit var googleMap: SupportMapFragment

    private var darkModePreference: Boolean = false

    private var fireShow = false
    private var weatherShow = false

    private val callback = OnMapReadyCallback { googleMap ->
        if (darkModePreference) changeMapStyle(googleMap)

        binding.btnFireRisk.setOnClickListener {
            fireShow = !fireShow
            updateMapVisibility(googleMap)
        }

        binding.btnCloudCoverage.setOnClickListener {
            weatherShow = !weatherShow
            updateMapVisibility(googleMap)
        }
    }

    private fun updateMapVisibility(googleMap: GoogleMap) {
        googleMap.clear()

        if (fireShow) {
            displayFireMap(googleMap)
            // Set active background color for btnFireRisk
            binding.btnFireRisk.setBackgroundColor(resources.getColor(R.color.fire_red, null))
        } else {
            // Set inactive background color for btnFireRisk
            binding.btnFireRisk.setBackgroundColor(resources.getColor(R.color.gray_400, null))
        }

        if (weatherShow) {
            displayWeatherMap(googleMap)
            // Set active background color for btnCloudCoverage
            binding.btnCloudCoverage.setBackgroundColor(resources.getColor(R.color.light_blue_600, null))
        } else {
            // Set inactive background color for btnCloudCoverage
            binding.btnCloudCoverage.setBackgroundColor(resources.getColor(R.color.gray_400, null))
        }
    }

    private fun displayWeatherMap(googleMap: GoogleMap) {
        val weatherTileProvider =
            WeatherTileProvider(requireContext(), viewModel.getWeatherApiService())
        googleMap.addTileOverlay(TileOverlayOptions().tileProvider(weatherTileProvider))
    }

    private fun displayFireMap(googleMap: GoogleMap) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        viewModel.getFireData(
            BuildConfig.FIRE_API_KEY, "MODIS_NRT", "ZAF", 1, today, googleMap
        )
        googleMap.setInfoWindowAdapter(FireMarkerInfoAdapter(requireContext()))
    }

    private fun changeMapStyle(googleMap: GoogleMap) {
        // Customise the styling of the base map using a JSON object defined.
        // Recommended by maps api https://developers.google.com/maps/documentation/android-sdk/styling#raw-resource
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(), R.raw.dark_style_json
                )
            )
            if (!success) {
                Log.e("Error", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("Error", "Can't find style. Error: ", e)
        }
    }

    override suspend fun addContentToView() {
        googleMap = binding.map.getFragment()
        viewModel.getDarkModePreference()
        viewModel.checkOfflineStatus()
    }

    private fun addMarkersToMap(
        fireList: MutableList<NetworkResult<FireLocationItemModel>>,
        googleMap: GoogleMap,
    ) {

        val fireIcon: BitmapDescriptor by lazy {

            BitmapHelper.vectorToBitmap(requireContext(), R.drawable.fire_24, R.color.fire_red)
        }

        var lastItem: FireLocationItemModel? = null

        for (fireLocationItem in fireList) {
            if (fireLocationItem is NetworkResult.Error) continue
            fireLocationItem.onSuccess {
                val latLng = LatLng(it.lat, it.long)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("Fire Location")
                        .icon(fireIcon)
                )?.tag = it
                lastItem = it
            }

        }

        if (fireList.isEmpty()) return
        lastItem?.let {
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        it.lat,
                        it.long
                    )
                )
            )
        }
    }


    override fun updateState(state: IMapState) {
        binding.progressBar.isVisible = state.isLoading
        darkModePreference = state.isDarkMode
        state.isOffline?.let {
            if (it) {
                binding.offlineMode.isVisible = true
                binding.offlineMode.text = getString(R.string.maps_offline_mode)
            } else {
                binding.offlineMode.isVisible = false
                googleMap.getMapAsync(callback)
            }
        }

        state.googleMap?.let {
            addMarkersToMap(state.items.toMutableList(), it)
        }


    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMapsBinding = FragmentMapsBinding.inflate(inflater, container, false)
}