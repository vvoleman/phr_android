package cz.vvoleman.phr.feature_medicine.ui.addedit.view

import android.util.Log
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.feature_medicine.databinding.FragmentAddEditMedicineBinding
import cz.vvoleman.phr.feature_medicine.presentation.addedit.model.AddEditMedicineViewState
import cz.vvoleman.phr.feature_medicine.ui.mapper.add_edit.TimeUiModelToPresentationMapper
import cz.vvoleman.phr.feature_medicine.ui.mapper.list.MedicineUiModelToPresentationMapper
import kotlinx.coroutines.launch

class AddEditMedicineBinder(
    private val medicineMapper: MedicineUiModelToPresentationMapper,
    private val timeMapper: TimeUiModelToPresentationMapper
) : BaseViewStateBinder<AddEditMedicineViewState, FragmentAddEditMedicineBinding, AddEditMedicineBinder.Notification>() {

    override fun bind(viewBinding: FragmentAddEditMedicineBinding, viewState: AddEditMedicineViewState) {
        lifecycleScope.launch {
            viewBinding.apply {
                medicineSelector.setData(viewState.medicines.map { medicineMapper.toUi(it) })
            }
        }

        Log.d("AddEditMedicineBinder", "bind: ${viewState.times}")
        viewBinding.timeSelector.setTimes(viewState.times.map { timeMapper.toUi(it) })
    }

    sealed class Notification
}
