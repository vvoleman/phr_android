package cz.vvoleman.phr.featureMedicine.ui.addEdit.view

import android.util.Log
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMedicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.addEdit.model.AddEditMedicineViewState
import cz.vvoleman.phr.featureMedicine.ui.mapper.addEdit.FrequencyDayUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.addEdit.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.list.MedicineUiModelToPresentationMapper
import kotlinx.coroutines.launch

@Suppress("MaximumLineLength", "MaxLineLength")
class AddEditMedicineBinder(
    private val medicineMapper: MedicineUiModelToPresentationMapper,
    private val timeMapper: TimeUiModelToPresentationMapper,
    private val frequencyMapper: FrequencyDayUiModelToPresentationMapper
) : BaseViewStateBinder<AddEditMedicineViewState, FragmentAddEditMedicineBinding, AddEditMedicineBinder.Notification>() {

    override fun bind(viewBinding: FragmentAddEditMedicineBinding, viewState: AddEditMedicineViewState) {
        lifecycleScope.launch {
            viewBinding.apply {
                medicineSelector.setData(viewState.medicines.map { medicineMapper.toUi(it) })
            }
        }

        Log.d("AddEditMedicineBinder", "bind: ${viewState.times}")
        viewBinding.timeSelector.setTimes(viewState.times.map { timeMapper.toUi(it) })
        viewBinding.frequencySelector.setDays(viewState.frequencyDays.map { frequencyMapper.toUi(it) })

        viewBinding.medicineSelector.setSelectedMedicine(viewState.selectedMedicine?.let { medicineMapper.toUi(it) })

        if (!viewBinding.frequencySelector.hasEverydaySet()) {
            viewBinding.frequencySelector.setDaysEveryday(viewState.frequencyDaysDefault.map {
                frequencyMapper.toUi(it)
            })
        }


    }

    sealed class Notification
}
