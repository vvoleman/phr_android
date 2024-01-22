package cz.vvoleman.featureMeasurement.ui.view.addEdit

import android.app.TimePickerDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cz.vvoleman.featureMeasurement.R
import cz.vvoleman.featureMeasurement.databinding.FragmentAddEditMeasurementBinding
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementNotification
import cz.vvoleman.featureMeasurement.presentation.model.addEdit.AddEditMeasurementViewState
import cz.vvoleman.featureMeasurement.presentation.viewmodel.AddEditMeasurementViewModel
import cz.vvoleman.featureMeasurement.ui.component.fieldEditor.FieldEditor
import cz.vvoleman.featureMeasurement.ui.component.reminderTimeSelector.ReminderTimeSelector
import cz.vvoleman.featureMeasurement.ui.component.reminderTimeSelector.TimeUiModelToPresentationMapper
import cz.vvoleman.featureMeasurement.ui.mapper.addEdit.destination.AddEditMeasurementDestinationUiMapper
import cz.vvoleman.featureMeasurement.ui.mapper.core.MeasurementGroupFieldUiToPresentationMapper
import cz.vvoleman.featureMeasurement.ui.model.core.MeasurementGroupFieldUi
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.ui.component.frequencySelector.FrequencyDayUiModel
import cz.vvoleman.phr.common.ui.component.frequencySelector.FrequencySelector
import cz.vvoleman.phr.common.ui.mapper.frequencySelector.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.common.utils.TimeConstants
import cz.vvoleman.phr.common.utils.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMeasurementFragment :
    BaseFragment<AddEditMeasurementViewState, AddEditMeasurementNotification, FragmentAddEditMeasurementBinding>(),
    FieldEditor.FieldEditorListener, FrequencySelector.FrequencySelectorListener,
    ReminderTimeSelector.TimeSelectorListener {

    override val viewModel: AddEditMeasurementViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditMeasurementDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditMeasurementViewState, FragmentAddEditMeasurementBinding>

    @Inject
    lateinit var frequencyMapper: FrequencyDayUiModelToPresentationMapper

    @Inject
    lateinit var timeMapper: TimeUiModelToPresentationMapper

    @Inject
    lateinit var fieldMapper: MeasurementGroupFieldUiToPresentationMapper

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddEditMeasurementBinding {
        return FragmentAddEditMeasurementBinding.inflate(inflater, container, false)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun setupListeners() {
        super.setupListeners()

        binding.fieldEditor.setListener(this)
        binding.frequencySelector.setListener(this)
        binding.timeSelector.setListener(this)
        binding.buttonAddTime.setOnClickListener {
            openTimeDialog { time, _ ->
                viewModel.onAddTime(time)
            }
        }
        binding.buttonSave.setOnClickListener {
            lifecycleScope.launch { viewModel.onSave() }
        }

        val nameChanges = binding.editTextName.textChanges()
            .debounce(TimeConstants.DEBOUNCE_TIME)

        collectLatestLifecycleFlow(nameChanges) {
            viewModel.onNameUpdate(it.toString())
        }
    }

    override fun handleNotification(notification: AddEditMeasurementNotification) {
        when (notification) {
            is AddEditMeasurementNotification.EditItem -> {
                binding.fieldEditor.startEdit(notification.item)
            }
            is AddEditMeasurementNotification.MissingFields -> {
                showSnackbar(notification.fields.toString())
            }

            AddEditMeasurementNotification.SaveError -> {
                showSnackbar(cz.vvoleman.phr.common_datasource.R.string.error_cannot_save)
            }
        }
    }

    override fun onStartDialog(dialog: DialogFragment, item: MeasurementGroupFieldUi) {
        dialog.show(childFragmentManager, item.name)
    }

    override fun onFieldClick(item: MeasurementGroupFieldUi, position: Int) {
        binding.fieldEditor.startEdit(item)
    }

    override fun onFieldOptionsMenuClicked(item: MeasurementGroupFieldUi, view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.options_editor_field)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    binding.fieldEditor.startEdit(item)
                    true
                }
                R.id.action_delete -> {
                    viewModel.onDeleteField(fieldMapper.toPresentation(item))
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    override fun onAddField(item: MeasurementGroupFieldUi) {
        binding.fieldEditor.startEdit(item)
    }

    override fun onSave(item: MeasurementGroupFieldUi) {
        Log.d("AddEditMeasurementFragment", "onSave: $item")
        viewModel.onSaveField(fieldMapper.toPresentation(item))
    }

    private fun openTimeDialog(index: Int? = null, updateUnit: (LocalTime, Int?) -> Unit) {
        val time = index?.let { viewModel.onGetTime(it) } ?: LocalTime.now()

        val dialog = TimePickerDialog(
            context,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val updatedTime = LocalTime.of(selectedHour, selectedMinute)

                updateUnit(updatedTime, index)
            },
            time.hour,
            time.minute,
            true
        )

        dialog.show()
    }
    override fun onValueChange(days: List<FrequencyDayUiModel>) {
        viewModel.onFrequencyUpdate(days.map { frequencyMapper.toPresentation(it) })
    }

    override fun onTimeClick(index: Int, anchorView: View) {
        val popup = androidx.appcompat.widget.PopupMenu(requireContext(), anchorView)
        popup.inflate(R.menu.options_add_measurement_time)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    openTimeDialog(index) { updatedTime, index ->
                        viewModel.onTimeUpdate(index!!, updatedTime)
                    }
                    true
                }

                R.id.action_delete -> {
                    viewModel.onTimeDelete(index)
                    true
                }

                else -> false
            }
        }

        popup.show()
    }
}
