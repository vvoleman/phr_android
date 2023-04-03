package cz.vvoleman.phr.common.ui.view.addedit

import android.content.Context
import android.util.Log
import androidx.core.content.res.TypedArrayUtils.getText
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import cz.vvoleman.phr.common.presentation.model.addedit.AddEditViewState
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.FragmentAddEditPatientBinding
import kotlinx.coroutines.CoroutineScope

class AddEditPatientBinder : BaseViewStateBinder<AddEditViewState, FragmentAddEditPatientBinding, AddEditPatientBinder.Notification>() {


    override fun bind(viewBinding: FragmentAddEditPatientBinding, viewState: AddEditViewState) {
        viewState.patient?.let {
            val date = it.birthDate
            date?.let {
                viewBinding.datePicker.setDate(date)
            }

            viewBinding.textFieldName.setText(it.name)
        }

        Log.d("AddEditPatientBinder", "Has errors: ${viewState.hasErrors()}")
        if (viewState.hasErrors()) {
            if (viewState.errorFields.containsKey("name")) {
                viewBinding.textFieldName.error = fragmentContext.getText(R.string.field_error_empty)
            }
        }
    }

    sealed class Notification {}

}