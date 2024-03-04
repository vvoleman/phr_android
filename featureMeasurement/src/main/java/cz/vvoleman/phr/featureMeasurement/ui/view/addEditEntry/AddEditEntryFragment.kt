package cz.vvoleman.phr.featureMeasurement.ui.view.addEditEntry

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.ui.component.picker.DatePicker
import cz.vvoleman.phr.common.ui.component.picker.TimePicker
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentAddEditEntryBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryNotification
import cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry.AddEditEntryViewState
import cz.vvoleman.phr.featureMeasurement.presentation.viewmodel.AddEditEntryViewModel
import cz.vvoleman.phr.featureMeasurement.ui.mapper.addEditEntry.destination.AddEditEntryDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class AddEditEntryFragment :
    BaseFragment<AddEditEntryViewState, AddEditEntryNotification, FragmentAddEditEntryBinding>(),
    TimePicker.TimePickerListener, DatePicker.DatePickerListener {

    override val viewModel: AddEditEntryViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditEntryDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditEntryViewState, FragmentAddEditEntryBinding>

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddEditEntryBinding {
        return FragmentAddEditEntryBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        val binder = (viewStateBinder as AddEditEntryBinder)
        collectLatestLifecycleFlow(binder.notification) {
            when (it) {
                is AddEditEntryBinder.Notification.EntryFieldValueChanged -> {
                    viewModel.onEntryFieldValueChange(it.id, it.value)
                }

                is AddEditEntryBinder.Notification.Submit -> {
                    Log.d(TAG, "Submit")
                    viewModel.onSave()
                }
            }
        }
        binding.timePicker.setListener(this)
        binding.datePicker.setListener(this)
//        binding.editTextDate.setOnClickListener {
//            val value = binding.editTextDate.text.toString()
//            val date = if (value.isBlank()) {
//                LocalDate.now()
//            } else {
//                LocalDate.parse(value)
//            }
//            showDatePickerDialog(date)
//        }

    }

    override fun handleNotification(notification: AddEditEntryNotification) {
        when (notification) {
            is AddEditEntryNotification.FieldErrors -> {
                showSnackbar(cz.vvoleman.phr.common_datasource.R.string.error_missing_fields)
            }
        }
    }

    override fun onTimeSelected(time: LocalTime) {
        viewModel.onTimeChange(time)
    }

    override fun onDateSelected(date: LocalDate) {
        viewModel.onDateChange(date)
    }

    override fun injectFragmentManager(): FragmentManager {
        return childFragmentManager
    }

//    private fun showDatePickerDialog(defaultDate: LocalDate) {
//        val datePicker =
//            MaterialDatePicker.Builder.datePicker()
//                .setTitleText(R.string.field_entry_date)
//                .setSelection(defaultDate.toEpochDay() * 1000)
//                .build()
//
//        datePicker.addOnPositiveButtonClickListener {
//            val date = LocalDate.ofEpochDay(it / 1000)
//            binding.editTextDate.setText(date.toLocalString())
//            viewModel.onDateChange(date)
//        }
//    }
}
