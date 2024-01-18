package cz.vvoleman.phr.featureMedicalRecord.ui.usecase

import android.util.Log
import com.google.mlkit.vision.common.InputImage
import cz.vvoleman.phr.featureMedicalRecord.presentation.usecase.TakePhotoPresentationUseCase

class TakePhotoUiUseCase : TakePhotoPresentationUseCase {

    override fun takePhoto(onSuccess: (InputImage) -> Unit) {
        Log.d("TakePhotoUiUseCase", "takePhoto")
    }
}
