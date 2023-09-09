package cz.vvoleman.phr.feature_medicalrecord.ui.mapper

import android.os.Bundle
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState

class AddEditViewStateToBundleMapper {

    fun toBundle(viewState: AddEditViewState): Bundle {
        return Bundle().apply {
            putParcelable(AddEditViewState.TAG, viewState)
        }
    }
}
