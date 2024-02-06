package cz.vvoleman.phr.featureEvent.ui.view.addEdit

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
import cz.vvoleman.phr.common.ui.mapper.healthcare.SpecificMedicalWorkerUiModelToPresentationMapper
import cz.vvoleman.phr.featureEvent.R
import cz.vvoleman.phr.featureEvent.databinding.FragmentAddEditEventBinding
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventNotification
import cz.vvoleman.phr.featureEvent.presentation.model.addEdit.AddEditEventViewState
import cz.vvoleman.phr.featureEvent.presentation.viewmodel.AddEditEventViewModel
import cz.vvoleman.phr.featureEvent.ui.mapper.addEdit.ReminderUiModelToPresentationMapper
import cz.vvoleman.phr.featureEvent.ui.mapper.addEdit.destination.AddEditEventDestinationUiMapper
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class AddEditEventFragment :
    BaseFragment<AddEditEventViewState, AddEditEventNotification, FragmentAddEditEventBinding>(),
    TimePicker.TimePickerListener, DatePicker.DatePickerListener {

    override val viewModel: AddEditEventViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditEventDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditEventViewState, FragmentAddEditEventBinding>

    @Inject
    lateinit var reminderMapper: ReminderUiModelToPresentationMapper

    @Inject
    lateinit var workerMapper: SpecificMedicalWorkerUiModelToPresentationMapper

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddEditEventBinding {
        return FragmentAddEditEventBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.timePickerStartAt.setListener(this)
        binding.datePickerStartAt.setListener(this)

        binding.buttonSave.setOnClickListener {
            viewModel.onSave()
        }

        val binder = (viewStateBinder as AddEditEventBinder)
        collectLatestLifecycleFlow(binder.notification) {
            when (it) {
                is AddEditEventBinder.Notification.ReminderClick -> {
                    viewModel.onReminderToggle(reminderMapper.toPresentation(it.reminder))
                }
                is AddEditEventBinder.Notification.AllRemindersToggle -> {
                    viewModel.onAllRemindersToggle()
                }
                is AddEditEventBinder.Notification.FieldDataChanged -> {
                    handleDataChange(it)
                }
            }
        }
    }

    override fun handleNotification(notification: AddEditEventNotification) {
        when (notification) {
            is AddEditEventNotification.MissingFields -> {
                showErrors(notification.fields)
            }
            is AddEditEventNotification.CannotSave -> {
                showSnackbar(R.string.error_cannot_save_event)
            }
        }
    }

    private fun showErrors(fields: List<AddEditEventViewState.RequiredField>) {

        val nameError = if (fields.contains(AddEditEventViewState.RequiredField.NAME)) {
            context?.getString(cz.vvoleman.phr.common_datasource.R.string.error_required)
        } else {
            null
        }

        val dateError = if (fields.contains(AddEditEventViewState.RequiredField.DATE)) {
            context?.getString(cz.vvoleman.phr.common_datasource.R.string.error_required)
        } else {
            null
        }

        val timeError = if (fields.contains(AddEditEventViewState.RequiredField.TIME)) {
            context?.getString(cz.vvoleman.phr.common_datasource.R.string.error_required)
        } else {
            null
        }

        binding.textInputLayoutEventName.error = nameError
        binding.datePickerStartAt.setError(dateError)
        binding.timePickerStartAt.setError(timeError)
    }

    private fun handleDataChange(notification: AddEditEventBinder.Notification.FieldDataChanged) {
        if (notification.name != null) {
            viewModel.onNameChanged(notification.name)
        }

        if (notification.description != null) {
            viewModel.onDescriptionChanged(notification.description)
        }

        if (notification.worker != null) {
            viewModel.onMedicalWorkerChanged(workerMapper.toPresentation(notification.worker))
        }
    }

    override fun onTimeSelected(time: LocalTime) {
        Log.d("AddEditEventFragment", "onTimeSelected: $time")
        viewModel.onTimeChanged(time)
    }

    override fun onDateSelected(date: LocalDate) {
        viewModel.onDateChanged(date)
    }

    override fun injectFragmentManager(): FragmentManager {
        return childFragmentManager
    }

}
