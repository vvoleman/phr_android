package cz.vvoleman.phr.feature_medicine.ui.list.view

import android.content.Context
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.feature_medicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.feature_medicine.ui.mapper.list.MedicineUiModelToPresentationMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ListMedicineBinder(
    private val medicineMapper: MedicineUiModelToPresentationMapper
) : BaseViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding, ListMedicineBinder.Notification>() {

    override fun bind(viewBinding: FragmentListMedicineBinding, viewState: ListMedicineViewState) {
        lifecycleScope.launch {
            viewBinding.apply {
                medicineSelector.setData(viewState.medicines.map { medicineMapper.toUi(it) })
            }
        }
    }

    sealed class Notification {}
}