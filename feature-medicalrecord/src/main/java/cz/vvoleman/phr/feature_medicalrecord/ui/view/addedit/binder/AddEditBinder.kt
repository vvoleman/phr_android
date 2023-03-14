package cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit.binder

import android.view.View
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState


class AddEditBinder:
    ViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding> {

    override fun bind(
        viewBinding: FragmentAddEditMedicalRecordBinding,
        viewState: AddEditViewState
    ) {
        if (viewState.patient != null) {
            viewBinding.progressBar.visibility = View.GONE
            viewBinding.currentPatientButton.visibility = View.VISIBLE
            viewBinding.currentPatientButton.text = viewState.patient.name
        } else {
            viewBinding.progressBar.visibility = View.VISIBLE
            viewBinding.currentPatientButton.visibility = View.GONE
        }
    }
}