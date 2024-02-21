package cz.vvoleman.phr.featureMedicalRecord.presentation.model.cropImage

import android.net.Uri
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditPresentationModel

data class CropImageViewState(
    val originalUri: Uri,
    val parentViewState: AddEditPresentationModel,
)
