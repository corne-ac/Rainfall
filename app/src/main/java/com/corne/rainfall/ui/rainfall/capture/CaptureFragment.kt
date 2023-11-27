package com.corne.rainfall.ui.rainfall.capture

import LocationListItemAdapter
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.databinding.FragmentCaptureBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID


class CaptureFragment :
    BaseStateFragment<FragmentCaptureBinding, CaptureState, CaptureViewModel>() {
    override val viewModel: CaptureViewModel by hiltMainNavGraphViewModels()
    private val calendar: Calendar = Calendar.getInstance()
    private lateinit var locationAdapter: LocationListItemAdapter
    private var firstRun = true


    override fun updateState(state: CaptureState) {
        binding.saveBtn.isEnabled = viewModel.isFormValid()
        viewModel.setOnSuccessCallback {
            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Successfully added Rain Log!", Toast.LENGTH_SHORT)
                .show()
        }

        viewModel.setOnFailCallback {
            Toast.makeText(
                requireContext(),
                "Something went wrong. ${viewModel.state.value.error}",
                Toast.LENGTH_SHORT
            ).show()
        }

        setUpFormState(state)
        setUpPopupFields()

        //Spinner
        if (state.allLocationsList.isNotEmpty()) {

            val adapter =
                LocationListItemAdapter(requireContext(), state.allLocationsList.map { it.name })
            binding.locationSpinner.adapter = adapter


            val selectedIndex =
                state.allLocationsList.indexOfFirst { it.locationUID == state.locationUid }

            // Set the selection in the Spinner
            if (selectedIndex != -1) {
                binding.locationSpinner.setSelection(selectedIndex)
            }


            fun onItemSelectedListener() = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedLocation: LocationModel = state.allLocationsList[position]
                    viewModel.updateLocation(selectedLocation.locationUID)
                    /*Log.d("RAIN_TAG_C", position.toString())
                    Log.d("RAIN_TAG_C", state.allLocationsList.size.toString())
                    Log.d("RAIN_TAG_C", state.allLocationsList[position].locationUID.toString())

                    //Set one to user for rainfall saving?
                    viewModel.updateLocation(state.allLocationsList[position].locationUID)*/
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

            binding.locationSpinner.onItemSelectedListener = onItemSelectedListener()
        }

    }

    private fun setUpFormState(state: CaptureState) {
        state.formValues.forEach {
            val value = it.value
            when (it.key) {
                CaptureForm.DATE -> showError(
                    value.errorMessage, binding.dateInput.binding.valueInputLayout
                )

                CaptureForm.START_TIME -> showError(
                    value.errorMessage, binding.startTimeInput.binding.valueInputLayout
                )

                CaptureForm.END_TIME -> showError(
                    value.errorMessage, binding.endTimeInput.binding.valueInputLayout
                )

                CaptureForm.RAIN_MM -> showError(
                    value.errorMessage, binding.rainMmInput.binding.valueInputLayout
                )

                CaptureForm.NOTES -> showError(
                    value.errorMessage, binding.rainNotes.binding.valueInputLayout
                )
            }
        }
    }

    private fun setUpPopupFields() {
        binding.dateInput.binding.value.inputType = 0
        binding.dateInput.binding.value.isFocusable = false
        binding.dateInput.binding.value.setOnClickListener { showDatePicker() }

        binding.startTimeInput.binding.value.inputType = 0
        binding.startTimeInput.binding.value.isFocusable = false
        binding.startTimeInput.binding.value.setOnClickListener { showTimePicker(binding.startTimeInput.binding.value) }

        binding.endTimeInput.binding.value.inputType = 0
        binding.endTimeInput.binding.value.isFocusable = false
        binding.endTimeInput.binding.value.setOnClickListener { showTimePicker(binding.endTimeInput.binding.value) }
    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            //set text of textinput
            val format = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            binding.dateInput.binding.value.setText(sdf.format(calendar.time))
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

    private fun showTimePicker(value: TextInputEditText) {
        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                // when user sets the time in the time picker dialog, update the calendar instance
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                // update the label text with the selected time
                val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                val timeString = sdf.format(Date(calendar.timeInMillis))
                // update the button text with the selected time
                value.setText(timeString)
            },
            // set the initial time in the time picker dialog to the current time
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            // set the time picker dialog to display in 24-hour format
            true
        )
        timePicker.show()
    }

    private fun showError(errorMessage: Int?, valueInputLayout: TextInputLayout) {
        errorMessage?.let { errorMsg ->
            valueInputLayout.error = requireContext().getString(errorMsg)
        } ?: run {
            valueInputLayout.error = null
        }
    }


    fun showProgressLoader(show: Boolean) {
        if (show) {

        } else {

        }
    }

    override suspend fun addContentToView() {
        viewModel.setUpForm()
        viewModel.loadUserLocationData()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        binding.dateInput.binding.value.doAfterTextChanged {
            viewModel.updateState(CaptureForm.DATE, it.toString())
        }
        binding.startTimeInput.binding.value.doAfterTextChanged {
            viewModel.updateState(CaptureForm.START_TIME, it.toString())
        }
        binding.endTimeInput.binding.value.doAfterTextChanged {
            viewModel.updateState(CaptureForm.END_TIME, it.toString())
        }
        binding.rainMmInput.binding.value.doAfterTextChanged {
            viewModel.updateState(CaptureForm.RAIN_MM, it.toString())
        }
        binding.rainNotes.binding.value.doAfterTextChanged {
            viewModel.updateState(CaptureForm.NOTES, it.toString())
        }
        binding.saveBtn.setOnClickListener { viewModel.add() }

        //Set default values

        val currentDate = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        binding.dateInput.binding.value.setText(currentDate.format(Date()))

        val currentTime = java.text.SimpleDateFormat("HH:mm", Locale.ENGLISH)
        binding.startTimeInput.binding.value.setText(currentTime.format(Date()))

        val timeCal = Calendar.getInstance()
        timeCal.add(Calendar.HOUR, 1)
        binding.endTimeInput.binding.value.setText(currentTime.format(timeCal.time))
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCaptureBinding = FragmentCaptureBinding.inflate(inflater, container, false)
}
