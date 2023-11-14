package cz.vvoleman.phr.featureMedicine.ui.addEdit.view

import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.viewmodel.AddEditMedicineViewModel
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.AddEditMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.component.frequencySelector.FrequencyDayUiModel
import cz.vvoleman.phr.featureMedicine.ui.component.frequencySelector.FrequencySelector
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.list.mapper.MedicineUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.component.medicineSelector.MedicineSelector
import cz.vvoleman.phr.featureMedicine.ui.list.model.MedicineUiModel
import cz.vvoleman.phr.featureMedicine.ui.component.timeSelector.TimeSelector
import cz.vvoleman.phr.featureMedicine.ui.component.timeSelector.TimeUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMedicineFragment :
    BaseFragment<AddEditMedicineViewState, AddEditMedicineNotification, FragmentAddEditMedicineBinding>(),
    MedicineSelector.MedicineSelectorListener,
    TimeSelector.TimeSelectorListener, FrequencySelector.FrequencySelectorListener {

    override val viewModel: AddEditMedicineViewModel by viewModels()

    override val TAG = "AddEditMedicineFragment"

    @Inject
    override lateinit var destinationMapper: AddEditMedicineDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditMedicineViewState, FragmentAddEditMedicineBinding>

    @Inject
    lateinit var timeUiModelToPresentationMapper: TimeUiModelToPresentationMapper

    @Inject
    lateinit var frequencyDayUiModelToPresentationMapper: FrequencyDayUiModelToPresentationMapper

    @Inject
    lateinit var medicineUiModelToPresentationMapper: MedicineUiModelToPresentationMapper

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

        binding.buttonAddTime.setOnClickListener {
            openTimeDialog { time, _ ->
                viewModel.onTimeAdd(time)
            }
        }

        binding.buttonSave.setOnClickListener {
            lifecycleScope.launch { viewModel.onSave() }
        }
    }

    override fun handleNotification(notification: AddEditMedicineNotification) {
        when (notification) {
            is AddEditMedicineNotification.DataLoaded -> {
                Snackbar.make(binding.root, "Data loaded", Snackbar.LENGTH_SHORT).show()
            }

            is AddEditMedicineNotification.CannotSave -> {
                Snackbar.make(binding.root, "Cannot save", Snackbar.LENGTH_SHORT).show()
            }

            is AddEditMedicineNotification.MedicineScheduleNotFound -> {
                Snackbar.make(binding.root, "Medicine schedule not found", Snackbar.LENGTH_SHORT).show()
            }

            is AddEditMedicineNotification.CannotScheduleMedicine -> {
                Snackbar.make(binding.root, "Cannot schedule medicine", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMedicineSelected(medicine: MedicineUiModel?) {
        viewModel.onMedicineSelected(medicine?.let { medicineUiModelToPresentationMapper.toPresentation(it) })
    }

    override fun onMedicineSelectorSearch(query: String) {
        viewModel.onSearch(query)
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
            timeUiModelToPresentationMapper.toUi(viewModel.onGetTime(index))
        } else {
            TimeUiModel(null, LocalTime.now(), 1)
        }

        val dialog = TimePickerDialog(
            context,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val updatedTime = time.copy(time = LocalTime.of(selectedHour, selectedMinute))

                updateUnit(timeUiModelToPresentationMapper.toPresentation(updatedTime), index)
            },
            time.time.hour,
            time.time.minute,
            true
        )

        dialog.show()
    }

    override fun onValueChange(days: List<FrequencyDayUiModel>) {
        viewModel.onFrequencyUpdate(days.map { frequencyDayUiModelToPresentationMapper.toPresentation(it) })
    }

}
