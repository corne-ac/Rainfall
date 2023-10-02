package com.corne.rainfall.ui.map

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.corne.rainfall.BuildConfig
import com.corne.rainfall.R
import com.corne.rainfall.api.WeatherApiService
import com.corne.rainfall.data.model.FireLocationItemModel
import com.corne.rainfall.databinding.FragmentMapsBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import com.corne.rainfall.utils.BitmapHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.TileOverlayOptions
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class MapsFragment : BaseStateFragment<FragmentMapsBinding, IMapState, MapViewModel>() {

    override val viewModel: MapViewModel by hiltMainNavGraphViewModels()
    private lateinit var googleMap: SupportMapFragment

    private var darkModePreference: Boolean = false

    private val callback = OnMapReadyCallback { googleMap ->
        if (darkModePreference) changeMapStyle(googleMap)
        //TODO: Do relevant call depending on user choice
        displayFireMap(googleMap)
        displayWeatherMap(googleMap)
    }

    private fun displayWeatherMap(googleMap: GoogleMap) {
        val weatherTileProvider = WeatherTileProvider(requireContext(), viewModel.getWeatherApiService())
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
        googleMap.getMapAsync(callback)
    }

    private fun addMarkersToMap(
        fireList: MutableList<FireLocationItemModel>,
        googleMap: GoogleMap,
    ) {
        val fireIcon: BitmapDescriptor by lazy {
            BitmapHelper.vectorToBitmap(requireContext(), R.drawable.fire_24)
        }

        for (fireLocationItem in fireList) {
            val latLng = LatLng(fireLocationItem.lat, fireLocationItem.long)

            googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Fire Location")
                    .icon(fireIcon)
            )?.tag = fireLocationItem
        }
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLng(
                LatLng(
                    fireList.last().lat,
                    fireList.last().long
                )
            )
        )
    }


    override fun updateState(state: IMapState) {
        binding.progressBar.isVisible = state.isLoading
        darkModePreference = state.isDarkMode
        state.googleMap?.let {
            addMarkersToMap(state.items.toMutableList(), it)
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMapsBinding = FragmentMapsBinding.inflate(inflater, container, false)
}

//    private fun displayWeatherMap(googleMap: GoogleMap) {
//        val layer = "clouds_new"
//        val z = "0" //Info n Z, X, Y usage: https://openweathermap.org/faq#zoom_levels
//        val x = "0"
//        val y = "0"
//        val apiKey = BuildConfig.WEATHER_API_KEY
//
//        val call = weatherApiService.getWeatherLayer(layer, z, x, y, apiKey)
//
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    val weatherLayer = response.body()
//                    val bitmap = BitmapFactory.decodeStream(weatherLayer!!.byteStream())
//                    val bounds = LatLngBounds(
//                        LatLng(-90.0, -180.0),  // South-west corner (bottom-left)
//                        LatLng(90.0, 180.0)    // North-east corner (top-right)
//                    )
//
//                    val newarkLatLng = LatLng(40.714086, -74.228697)
//                    val newarkMap = GroundOverlayOptions()
//                        .image(
//                            BitmapHelper.vectorToBitmap(
//                                requireContext(),
//                                R.drawable.ic_launcher_background
//                            )
//                        )
//                        .position(newarkLatLng, 80600f, 60500f)
//                    val gg = googleMap.addGroundOverlay(newarkMap)
//                    gg?.isVisible = true
//                    gg!!.zIndex = 100f
////                    val groundOverlayOptions = GroundOverlayOptions()
////                        .image(BitmapDescriptorFactory.fromBitmap(bitmap))
////                        .positionFromBounds(bounds)
////
////                    val groundOverlay = googleMap.addGroundOverlay(groundOverlayOptions)
////                    groundOverlay!!.zIndex = 100f
//
//                } else {
//                    // Handle unsuccessful response
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                // Handle network error
//                t.printStackTrace()
//                val i = 1
//            }
//        })
//    }

