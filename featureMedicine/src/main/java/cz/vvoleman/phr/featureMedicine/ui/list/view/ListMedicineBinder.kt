package cz.vvoleman.phr.featureMedicine.ui.list.view

import android.util.Log
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.ui.mapper.list.MedicineUiModelToPresentationMapper

@Suppress("UnusedPrivateProperty")
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
