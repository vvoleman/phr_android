package cz.vvoleman.phr.featureMedicalRecord.ui.view.detail

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentDetailMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordViewState

class DetailMedicalRecordBinder :
    BaseViewStateBinder<DetailMedicalRecordViewState, FragmentDetailMedicalRecordBinding, DetailMedicalRecordBinder.Notification>() {


    override fun firstBind(viewBinding: FragmentDetailMedicalRecordBinding, viewState: DetailMedicalRecordViewState) {
        super.firstBind(viewBinding, viewState)

        viewBinding.textViewTemp.text = viewState.medicalRecord.toString()
    }

    sealed class Notification {

    }
}
