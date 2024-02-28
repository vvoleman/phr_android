package cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AssetPresentationModel

class SelectFileDestination : PresentationDestination {

    object Cancel : PresentationDestination
    data class SuccessWithOptions(
        val parentViewState: AddEditPresentationModel,
        val selectedOptions: SelectedOptionsPresentationModel,
        val fileAsset: AssetPresentationModel
    ) : PresentationDestination

    data class Success(val parentViewState: AddEditPresentationModel, val fileAsset: AssetPresentationModel) :
        PresentationDestination
}
