package com.corne.rainfall.ui.graph

import android.app.DatePickerDialog
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.icu.text.SimpleDateFormat
import android.provider.MediaStore
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.HoverMode
import com.anychart.enums.TooltipPositionMode
import com.corne.rainfall.R
import com.corne.rainfall.data.model.RainfallData
import com.corne.rainfall.databinding.FragmentGraphBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.components.TextInput
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import java.util.Calendar
import java.util.Date
import java.util.Locale


class RainGraphFragment : BaseStateFragment<FragmentGraphBinding, IGraphState, GraphViewModel>() {
    override val viewModel: GraphViewModel by hiltMainNavGraphViewModels()
    private val calendar: Calendar = Calendar.getInstance()
    private var filter = false
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun updateState(state: IGraphState) {
        // When the state changes, update the UI
        if (state.isLoading) {
            //TODO: show loading

            binding.downloadGraph.visibility = View.GONE
            binding.switchGraph.visibility = View.GONE

        }

        if (state.error != null) {
            //TODO: show error
        }

        if (state.rainDataList.isNotEmpty() && state.rainDataList.count() > 1) {
            binding.addrainMessage.visibility = View.GONE
            val icon = if (state.isGraphBar) R.drawable.line_chart_24 else R.drawable.bar_chart_24
            binding.switchGraph.icon = AppCompatResources.getDrawable(requireContext(), icon)
            // TODO: not too sure if this should be done in the VM or here

            val list = state.rainDataList.sortedBy { it.date }
            val finalList = list.map { ValueDataEntry(sdf.format(it.date), it.mm) }
            setUpGraph(state, finalList, null)

            binding.startDate.binding.value.inputType = 0
            binding.startDate.binding.value.isFocusable = false
            binding.startDate.binding.value.setOnClickListener { showDatePicker(binding.startDate) }

            binding.endDate.binding.value.inputType = 0
            binding.endDate.binding.value.isFocusable = false
            binding.endDate.binding.value.setOnClickListener { showDatePicker(binding.endDate) }

            binding.filterBtn.setOnClickListener {
                val startDate = binding.startDate.getText()
                val endDate = binding.endDate.getText()
                val title = "$startDate - > $endDate"
                filter = true
                viewModel.setState { rainDataList =  filterByDates(startDate, endDate, viewModel.state.value.rainDataList)}

                val list = state.rainDataList.map { ValueDataEntry(sdf.format(it.date), it.mm) }

                //Order list
                setUpGraph(state, list, title)
                binding.filterCard.visibility = View.GONE
            }


            binding.downloadGraph.visibility = View.VISIBLE
            binding.switchGraph.visibility = View.VISIBLE
        } else {
            //Show dialog thing
            binding.addrainMessage.visibility = View.VISIBLE

        }



    }

    fun filterByDates(startDate: String, endDate: String, list: List<RainfallData>): List<RainfallData> {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Parse start and end dates
        val startDateObj = sdf.parse(startDate)
        val endDateObj = sdf.parse(endDate)

        // Filter the list based on date range
        val filteredList = list.filter { data ->
            val dataDate = data.date
            dataDate in startDateObj..endDateObj
        }
        val sortedList = filteredList.sortedBy { it.date }
        // Convert to ValueDataEntry
        return sortedList
    }

    //The Below graph code was derived from AnyChart
    //https://www.anychart.com/products/anychart/overview/

    private fun setUpGraph(
        state: IGraphState,
        list: List<ValueDataEntry>,
        title: String?
    ) {
        val frameLayout = binding.frameLayout
        // Clear the previous graph.
        frameLayout.removeAllViews()
        // Fun times the anyChartView can only call setChart once so in our case we must create a new one each time.
        val anyChartView = AnyChartView(requireContext())
        frameLayout.addView(anyChartView)


        val cartesian = if (state.isGraphBar) AnyChart.column() else AnyChart.line()
        val base = if (state.isGraphBar) cartesian.column(list) else cartesian.line(list)
        cartesian.background().enabled(true);


        val primaryColor = getFromTheme(com.google.android.material.R.attr.colorPrimary)
        if (primaryColor != null) base.color(String.format("#%06X", 0xFFFFFF and primaryColor))

        val surfaceColor = getFromTheme(com.google.android.material.R.attr.colorSurface)
        if (surfaceColor != null) cartesian.background()
            .fill(String.format("#%06X", 0xFFFFFF and surfaceColor));

        // Setup the base chart
        cartesian.animation(true)
        if (title != null) cartesian.title(title)
        else cartesian.title("Rainfall for the last 7 days")
        cartesian.yScale().minimum(0.0)
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: } mm")
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)
        cartesian.xAxis(0).title("Date")
        cartesian.yAxis(0).title("Rainfall (mm)")
        cartesian.credits().enabled(false)
        // Set the chart on the view
        anyChartView.setChart(cartesian)
    }

    private fun getFromTheme(
        res: Int,
    ): Int? {
        val typedValue = TypedValue()
        val theme = requireContext().theme
        val attributeResolved = theme.resolveAttribute(
            res, typedValue, true
        )
        if (attributeResolved) return typedValue.data
        return null
    }

    override suspend fun addContentToView() {
        viewModel.getDataForGraph()

        binding.filterCard.visibility = View.GONE

        binding.switchGraph.setOnClickListener {
            viewModel.updateGraphType()
        }
        binding.downloadGraph.setOnClickListener {
            saveChartAsImage(binding.frameLayout, "chart.png")
        }

        binding.filterGraph.setOnClickListener {
            if (binding.filterCard.visibility == View.VISIBLE)
                binding.filterCard.visibility = View.GONE
            else
                binding.filterCard.visibility = View.VISIBLE
        }

        //set start of year
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        calendar.set(Calendar.MONTH, Calendar.JANUARY)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        binding.startDate.binding.value.setText(currentDate.format(calendar.time))

        calendar.set(Calendar.MONTH, Calendar.DECEMBER)
        calendar.set(Calendar.DAY_OF_MONTH, 31)
        binding.endDate.binding.value.setText(currentDate.format(calendar.time))

        calendar.time = Date()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
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
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
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

    private fun showDatePicker(input: TextInput) {
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            //set text of textinput
            val format = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            input.binding.value.setText(sdf.format(calendar.time))
        }

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            datePicker,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Set the maximum date selectable to today's date
        val datePicker1 = datePickerDialog.datePicker
        datePicker1.maxDate = System.currentTimeMillis()

        // Show the date picker dialog
        datePickerDialog.show()
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentGraphBinding = FragmentGraphBinding.inflate(inflater, container, false)
}
