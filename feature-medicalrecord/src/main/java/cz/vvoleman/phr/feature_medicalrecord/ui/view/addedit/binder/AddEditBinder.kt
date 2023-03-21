package cz.vvoleman.phr.feature_medicalrecord.ui.view.addedit.binder

import android.content.Context
import android.view.View
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.feature_medicalrecord.databinding.FragmentAddEditMedicalRecordBinding
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate


class AddEditBinder:
    ViewStateBinder<AddEditViewState, FragmentAddEditMedicalRecordBinding> {

    override fun bind(
        viewBinding: FragmentAddEditMedicalRecordBinding,
        viewState: AddEditViewState
    ) {
        viewBinding.datePicker.setDate(viewState.createdAt ?: LocalDate.now())

//        if (viewState.patient != null) {
//            viewBinding.progressBar.visibility = View.GONE
//            viewBinding.currentPatientButton.visibility = View.VISIBLE
//            viewBinding.currentPatientButton.text = viewState.patient.name
//        } else {
//            viewBinding.progressBar.visibility = View.VISIBLE
//            viewBinding.currentPatientButton.visibility = View.GONE
//        }
    }

    override fun init(viewBinding: FragmentAddEditMedicalRecordBinding, context: Context, lifecycleScope: CoroutineScope) {

    }
}