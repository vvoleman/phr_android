package cz.vvoleman.phr.common.ui.view.healthcare.addEdit

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerViewState
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditMedicalWorkerBinding

class AddEditMedicalWorkerBinder :
    BaseViewStateBinder<AddEditMedicalWorkerViewState, FragmentAddEditMedicalWorkerBinding, AddEditMedicalWorkerBinder.Notification>() {

    private var facilityAdapter: FacilityAdapter? = null

    override fun bind(viewBinding: FragmentAddEditMedicalWorkerBinding, viewState: AddEditMedicalWorkerViewState) {
        facilityAdapter?.submitList(viewState.details)
    }

    fun setFacilityAdapter(facilityAdapter: FacilityAdapter) {
        this.facilityAdapter = facilityAdapter
    }

    override fun onDestroy(viewBinding: FragmentAddEditMedicalWorkerBinding) {
        super.onDestroy(viewBinding)

        facilityAdapter = null
    }

    sealed class Notification
}
