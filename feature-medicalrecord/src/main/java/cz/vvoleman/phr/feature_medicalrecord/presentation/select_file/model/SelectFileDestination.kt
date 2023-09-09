package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AssetPresentationModel

class SelectFileDestination : PresentationDestination {

    object Cancel : PresentationDestination
    data class SuccessWithOptions(
        val parentViewState: AddEditViewState,
        val selectedOptions: SelectedOptionsPresentationModel,
        val fileAsset: AssetPresentationModel
    ) : PresentationDestination

    data class Success(val parentViewState: AddEditViewState, val fileAsset: AssetPresentationModel) :
        PresentationDestination
}
