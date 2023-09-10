package cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.model

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AddEditViewState
import cz.vvoleman.phr.featureMedicalRecord.presentation.addEdit.model.AssetPresentationModel

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
