package cz.vvoleman.phr.featureMedicine.ui.addEdit.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.map
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.common.ui.component.frequencySelector.FrequencyDayUiModel
import cz.vvoleman.phr.common.ui.component.frequencySelector.FrequencySelector
import cz.vvoleman.phr.common.ui.mapper.frequencySelector.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.viewmodel.AddEditMedicineViewModel
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.AddEditMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.component.medicineSelector.MedicineSelector
import cz.vvoleman.phr.featureMedicine.ui.component.timeSelector.TimeSelector
import cz.vvoleman.phr.featureMedicine.ui.component.timeSelector.TimeUiModel
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.model.MedicineUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMedicineFragment :
    BaseFragment<AddEditMedicineViewState, AddEditMedicineNotification, FragmentAddEditMedicineBinding>(),
    MedicineSelector.MedicineSelectorListener,
    TimeSelector.TimeSelectorListener,
    FrequencySelector.FrequencySelectorListener {

    override val viewModel: AddEditMedicineViewModel by viewModels()

    override val TAG = "AddEditMedicineFragment"

    @Inject
    override lateinit var destinationMapper: AddEditMedicineDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditMedicineViewState, FragmentAddEditMedicineBinding>

    @Inject
    lateinit var timeMapper: TimeUiModelToPresentationMapper

    @Inject
    lateinit var frequencyDayMapper: FrequencyDayUiModelToPresentationMapper

    @Inject
    lateinit var medicineMapper: MedicineUiModelToPresentationMapper

    override fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddEditMedicineBinding {
        return FragmentAddEditMedicineBinding.inflate(inflater, container, false)
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.medicineSelector.setListener(this)
        binding.timeSelector.setListener(this)
        binding.frequencySelector.setListener(this)

        binding.timeSelector.setLifecycleScope(lifecycleScope)

        binding.buttonAddTime.setOnClickListener {
            openTimeDialog { time, _ ->
                viewModel.onTimeAdd(time)
            }
        }

        binding.buttonSave.setOnClickListener {
            lifecycleScope.launch { viewModel.onSave() }
        }

        val binder = (viewStateBinder as AddEditMedicineBinder)
        collectLatestLifecycleFlow(binder.notification) {
            when (it) {
                is AddEditMedicineBinder.Notification.ProblemCategorySelected -> {
                    viewModel.onProblemCategorySelected(it.value)
                }
            }
        }
    }

    override fun handleNotification(notification: AddEditMedicineNotification) {
        when (notification) {

            is AddEditMedicineNotification.CannotSave -> {
                showSnackbar(R.string.add_edit_medicine_cannot_save)
            }

            is AddEditMedicineNotification.MedicineScheduleNotFound -> {
                showSnackbar(R.string.add_edit_medicine_schedule_not_found)
            }

            is AddEditMedicineNotification.CannotSchedule -> {
                showSnackbar(R.string.add_edit_medicine_cannot_schedule)
            }
        }
    }

    override fun onMedicineSelected(medicine: MedicineUiModel?) {
        viewModel.onMedicineSelected(medicine?.let { medicineMapper.toPresentation(it) })
    }

    override fun onMedicineSelectorSearch(
        query: String,
        callback: suspend (PagingData<MedicineUiModel>) -> Unit
    ) {
        val stream = viewModel.onMedicineSearch(query)

        lifecycleScope.launch {
            stream.map { pagingData ->
                pagingData.map { medicineMapper.toUi(it) }
            }.collectLatest(callback)
        }
    }

    override fun onTimeClick(index: Int, anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.inflate(R.menu.options_add_medicine_time)

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

    override fun onQuantityChange(index: Int, newValue: Number) {
        viewModel.onQuantityChange(index, newValue)
    }

    private fun openTimeDialog(index: Int? = null, updateUnit: (TimePresentationModel, Int?) -> Unit) {
        val time = if (index != null) {
            timeMapper.toUi(viewModel.onGetTime(index))
        } else {
            if (!viewModel.canAddTime()) {
                showSnackbar(R.string.add_edit_medicine_max_times)
                return
            }
            TimeUiModel(null, LocalTime.now(), 1)
        }

        val dialog = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setHour(time.time.hour)
            .setMinute(time.time.minute)
            .build()

        dialog.addOnPositiveButtonClickListener {
            val updatedTime = time.copy(time = LocalTime.of(dialog.hour, dialog.minute))

            updateUnit(timeMapper.toPresentation(updatedTime), index)
        }

        dialog.show(childFragmentManager, "time_picker")
    }

    override fun onValueChange(days: List<FrequencyDayUiModel>) {
        viewModel.onFrequencyUpdate(days.map { frequencyDayMapper.toPresentation(it) })
    }

}
