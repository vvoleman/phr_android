package cz.vvoleman.phr.feature_medicine.ui.list.view

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.feature_medicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.feature_medicine.presentation.list.model.ListMedicineViewState

class ListMedicineBinder : BaseViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding, ListMedicineBinder.Notification>() {

    override fun bind(viewBinding: FragmentListMedicineBinding, viewState: ListMedicineViewState) {
        viewBinding.textViewResult.text = "Počet: ${viewState.medicines.size}"
    }

    sealed class Notification {}
}