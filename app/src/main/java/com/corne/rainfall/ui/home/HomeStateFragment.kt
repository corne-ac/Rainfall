package com.corne.rainfall.ui.home

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentHomeBinding
import com.corne.rainfall.ui.base.BaseFragment


class HomeStateFragment : BaseFragment<FragmentHomeBinding>() {
    override suspend fun addContentToView() {
        val anyChartView: AnyChartView = binding.anyChartView

        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Rouge", 80540))
        data.add(ValueDataEntry("Foundation", 94190))
        data.add(ValueDataEntry("Mascara", 102610))
        data.add(ValueDataEntry("Lip gloss", 110430))
        data.add(ValueDataEntry("Lipstick", 128000))
        data.add(ValueDataEntry("Nail polish", 143760))
        data.add(ValueDataEntry("Eyebrow pencil", 170670))
        data.add(ValueDataEntry("Eyeliner", 213210))
        data.add(ValueDataEntry("Eyeshadows", 249980))

        val line = AnyChart.line()
        line.data(data)

//        val cartesian = AnyChart.cartesian()
//        val column = cartesian.column(data)
        val cartesian = AnyChart.line()
        val column = cartesian.line(data)
        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("\${%Value}{groupsSeparator: }")

        cartesian.animation(true)
        cartesian.title("Top 10 Cosmetic Products by Revenue")

        cartesian.yScale().minimum(0.0)

        cartesian.yAxis(0).labels().format("\${%Value}{groupsSeparator: }")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title("Product")
        cartesian.yAxis(0).title("Revenue")
        anyChartView.setChart(cartesian)
        cartesian.credits().enabled(false);

        binding.saveBtn.setOnClickListener {
            saveChartAsImage(anyChartView, "chart.png")
        }

//        binding.rainfallCaptured.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_rainfallListFragment)
//        }

    }

    fun saveChartAsImage(chartView: View, fileName: String) {
        val bitmap = Bitmap.createBitmap(chartView.width, chartView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        chartView.draw(canvas)
        val values = ContentValues()
        val timestamp = System.currentTimeMillis()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, timestamp)
        values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp)
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "Rainfall")
        values.put(MediaStore.Images.Media.IS_PENDING, true)
        val uri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        if (uri != null) {
            val outputStream = requireContext().contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                requireContext().contentResolver.update(uri, values, null, null)
            }
        } else {
            Toast.makeText(context, "Error saving chart", Toast.LENGTH_SHORT).show()
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}
