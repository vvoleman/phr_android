package cz.vvoleman.phr.featureMedicine.ui.addEdit.view

import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.base.ui.view.BaseFragment
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineNotification
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.viewmodel.AddEditMedicineViewModel
import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.TimePresentationModel
import cz.vvoleman.phr.featureMedicine.ui.addEdit.mapper.AddEditMedicineDestinationUiMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.addEdit.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.medicineSelector.MedicineSelector
import cz.vvoleman.phr.featureMedicine.ui.model.list.MedicineUiModel
import cz.vvoleman.phr.featureMedicine.ui.timeSelector.TimeSelector
import cz.vvoleman.phr.featureMedicine.ui.timeSelector.TimeUiModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class AddEditMedicineFragment :
    BaseFragment<AddEditMedicineViewState, AddEditMedicineNotification, FragmentAddEditMedicineBinding>(),
    MedicineSelector.MedicineSelectorListener,
    TimeSelector.TimeSelectorListener {

    override val viewModel: AddEditMedicineViewModel by viewModels()

    @Inject
    override lateinit var destinationMapper: AddEditMedicineDestinationUiMapper

    @Inject
    override lateinit var viewStateBinder: ViewStateBinder<AddEditMedicineViewState, FragmentAddEditMedicineBinding>

    @Inject
    lateinit var timeUiModelToPresentationMapper: TimeUiModelToPresentationMapper

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

        binding.buttonAddTime.setOnClickListener {
            openTimeDialog { time, _ ->
                viewModel.onTimeAdd(time)
            }
        }
    }

    override fun handleNotification(notification: AddEditMedicineNotification) {
        when (notification) {
            is AddEditMedicineNotification.DataLoaded -> {
                Snackbar.make(binding.root, "Data loaded", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMedicineSelected(medicine: MedicineUiModel?) {
        Snackbar.make(binding.root, "Medicine selected", Snackbar.LENGTH_SHORT).show()
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

    private fun openTimeDialog(index: Int? = null, updateUnit: (TimePresentationModel, Int?) -> Unit) {
        val time = if (index != null) {
            timeUiModelToPresentationMapper.toUi(viewModel.onGetTime(index))
        } else {
            TimeUiModel(LocalTime.now(), 1)
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
}
