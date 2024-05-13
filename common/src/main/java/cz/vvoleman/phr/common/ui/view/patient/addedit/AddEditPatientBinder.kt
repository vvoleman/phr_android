package cz.vvoleman.phr.common.ui.view.patient.addedit

import android.util.Log
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.presentation.model.patient.addedit.AddEditViewState
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditPatientBinding

@Suppress("MaximumLineLength")
class AddEditPatientBinder :
    BaseViewStateBinder<AddEditViewState, FragmentAddEditPatientBinding, AddEditPatientBinder.Notification>() {

    override fun firstBind(viewBinding: FragmentAddEditPatientBinding, viewState: AddEditViewState) {
        super.firstBind(viewBinding, viewState)

        viewState.patient?.let {
            val date = it.birthDate
            date?.let {
                viewBinding.datePicker.setDate(date)
            }

            viewBinding.textFieldName.setText(it.name)
        }
    }

    override fun bind(viewBinding: FragmentAddEditPatientBinding, viewState: AddEditViewState) {
        Log.d("AddEditPatientBinder", "Has errors: ${viewState.hasErrors()}")
        if (viewState.hasErrors()) {
            if (viewState.errorFields.containsKey("name")) {
                viewBinding.textFieldName.error = fragmentContext.getText(R.string.field_error_empty)
            }
        }
    }

    sealed class Notification
}
