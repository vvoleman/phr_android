package cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile

import android.net.Uri
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AssetPresentationModel

sealed class SelectFileDestination : PresentationDestination {

    object Cancel : SelectFileDestination()
    data class SuccessWithOptions(
        val parentViewState: AddEditPresentationModel,
        val selectedOptions: SelectedOptionsPresentationModel,
        val fileAsset: AssetPresentationModel
    ) : SelectFileDestination()

    data class Success(val parentViewState: AddEditPresentationModel, val fileAsset: AssetPresentationModel) :
        SelectFileDestination()

    data class CropImage(val uri: Uri, val parentViewState: AddEditPresentationModel) : SelectFileDestination()
}
