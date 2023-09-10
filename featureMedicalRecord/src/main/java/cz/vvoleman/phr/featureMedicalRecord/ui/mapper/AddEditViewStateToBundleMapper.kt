package cz.vvoleman.phr.featureMedicalRecord.ui.mapper

import android.os.Bundle
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AddEditViewState

class AddEditViewStateToBundleMapper {

    fun toBundle(viewState: AddEditViewState): Bundle {
        return Bundle().apply {
            putParcelable(AddEditViewState.TAG, viewState)
        }
    }
}
