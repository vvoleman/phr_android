package cz.vvoleman.phr.featureMedicalRecord.presentation.model.cropImage

import android.graphics.Bitmap
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditPresentationModel

sealed class CropImageDestination : PresentationDestination {
    object Cancel : CropImageDestination()
    data class CropImage(val parentViewState: AddEditPresentationModel, val result: Bitmap) : CropImageDestination()
}
