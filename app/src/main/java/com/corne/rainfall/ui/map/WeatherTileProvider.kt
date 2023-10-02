package com.corne.rainfall.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.corne.rainfall.BuildConfig
import com.corne.rainfall.api.WeatherApiService
import com.google.android.gms.maps.model.Tile
import com.google.android.gms.maps.model.TileProvider
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.ByteArrayOutputStream


class WeatherTileProvider(context: Context, val weatherApiService: WeatherApiService) :
    TileProvider {
    private val scaleFactor: Float
    private val borderTile: Bitmap
    companion object {
        private const val TILE_SIZE_DP = 256
    }
    init {
        /* Scale factor based on density, with a 0.6 multiplier to increase tile generation
         * speed */
        scaleFactor = context.resources.displayMetrics.density * 0.6f
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint.style = Paint.Style.STROKE
        borderTile = Bitmap.createBitmap(
            (TILE_SIZE_DP * scaleFactor).toInt(),
            (TILE_SIZE_DP * scaleFactor).toInt(), Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(borderTile)
        canvas.drawRect(
            0f,
            0f,
            TILE_SIZE_DP * scaleFactor,
            TILE_SIZE_DP * scaleFactor,
            borderPaint
        )
    }


    override fun getTile(x: Int, y: Int, zoom: Int): Tile {

        val layer = "clouds_new"
        val apiKey = BuildConfig.WEATHER_API_KEY

        var tempImg: ByteArray = ByteArray(0)

        val call = weatherApiService.getWeatherLayer(
            layer,
            zoom.toString(),
            x.toString(),
            y.toString(),
            apiKey
        )
        val c: Response<ResponseBody> = call.execute()

        if (c.isSuccessful) {
            val weatherLayer = c.body()
            val bitmap: Bitmap = BitmapFactory.decodeStream(weatherLayer!!.byteStream())
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
            tempImg = stream.toByteArray()

        } else {
            // Handle unsuccessful response
        }


        return Tile(
            (TILE_SIZE_DP * scaleFactor).toInt(),
            (TILE_SIZE_DP * scaleFactor).toInt(), tempImg
        )
    }

}
