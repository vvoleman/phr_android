package cz.vvoleman.phr.feature_medicalrecord.ui.view.binder

import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentListMedicalRecordsBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.list.model.ListMedicalRecordsViewState

class ButtonBinder: ViewStateBinder<ListMedicalRecordsViewState, FragmentListMedicalRecordsBinding> {

    override fun bind(
        viewBinding: FragmentListMedicalRecordsBinding,
        viewState: ListMedicalRecordsViewState
    ) {
        viewBinding.btn.text = viewState.groupBy.name
    }
}