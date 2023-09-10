package cz.vvoleman.phr.feature_medicine.ui.list.view

import android.util.Log
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.feature_medicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.feature_medicine.ui.mapper.list.MedicineUiModelToPresentationMapper

class ListMedicineBinder(
    private val medicineMapper: MedicineUiModelToPresentationMapper
) : BaseViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding, ListMedicineBinder.Notification>() {

    override fun bind(viewBinding: FragmentListMedicineBinding, viewState: ListMedicineViewState) {
        Log.d(TAG, "Not yet implemented: $viewState")
    }

    sealed class Notification

    companion object {
        const val TAG = "ListMedicineBinder"
    }
}
