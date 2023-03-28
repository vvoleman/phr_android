package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

import android.net.Uri
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState

class SelectFileDestination : PresentationDestination {

    object Cancel : PresentationDestination
    data class SuccessWithOptions(
        val parentViewState: AddEditViewState,
        val selectedOptions: SelectedOptionsPresentationModel,
        val fileUri: Uri
    ) : PresentationDestination

    data class Success(val parentViewState: AddEditViewState, val fileUri: Uri) :
        PresentationDestination

}