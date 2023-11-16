package com.corne.rainfall.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import com.corne.rainfall.BuildConfig
import com.corne.rainfall.api.WeatherApiService
import com.corne.rainfall.utils.Constants.MAP_TILE_SIZE_IN_DP
import com.google.android.gms.maps.model.Tile
import com.google.android.gms.maps.model.TileProvider
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.ByteArrayOutputStream


class WeatherTileProvider(context: Context, private val weatherApiService: WeatherApiService) :
    TileProvider {
    private val scaleFactor: Float
    private val borderTile: Bitmap

    init {
        // Calculate scaleFactor based on screen density.
        scaleFactor = context.resources.displayMetrics.density * 0.6f
        // Create a borderTile bitmap with a stroke style
        borderTile = createTile()
    }

    /**
     * Creates a border tile bitmap with a stroke style.
     *
     * @return The created border tile bitmap.
     */
    private fun createTile(): Bitmap {
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint.style = Paint.Style.STROKE
        val tile = Bitmap.createBitmap(
            (MAP_TILE_SIZE_IN_DP * scaleFactor).toInt(),
            (MAP_TILE_SIZE_IN_DP * scaleFactor).toInt(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(tile)

        canvas.drawRect(
            0f,
            0f,
            MAP_TILE_SIZE_IN_DP * scaleFactor,
            MAP_TILE_SIZE_IN_DP * scaleFactor,
            borderPaint
        )

        return tile
    }


    override fun getTile(x: Int, y: Int, zoom: Int): Tile {
        val layer = WeatherRenderType.CLOUDS.type
        val apiKey = BuildConfig.WEATHER_API_KEY
        // Create the call
        val call = weatherApiService.getWeatherLayer(layer, zoom, x, y, apiKey)
        val weatherResponse: Response<ResponseBody> = call.execute()

        val imageBytes = if (weatherResponse.isSuccessful) {
            // Get the body, containing the image from the response.
            val weatherLayer = weatherResponse.body()
            // Convert the image to a bitmap.
            val bitmap: Bitmap = BitmapFactory.decodeStream(weatherLayer!!.byteStream())
            // Convert the bitmap to a byte array.
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
            stream.toByteArray()
        } else {
            // Handle unsuccessful response.
            Log.e(
                "WeatherTileProvider",
                "Could not getTile info for x: $x y: $y zoom: $zoom http response code: ${weatherResponse.code()}"
            )
            // For now we just return an empty tile.
            ByteArray(0)
        }
        //
        return Tile(
            (MAP_TILE_SIZE_IN_DP * scaleFactor).toInt(),
            (MAP_TILE_SIZE_IN_DP * scaleFactor).toInt(),
            imageBytes
        )
    }

}
