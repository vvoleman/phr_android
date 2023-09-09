package cz.vvoleman.phr.feature_medicalrecord.ui.usecase

import android.util.Log
import com.google.mlkit.vision.common.InputImage
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.usecase.TakePhotoPresentationUseCase

class TakePhotoUiUseCase : TakePhotoPresentationUseCase {

    override fun takePhoto(onSuccess: (InputImage) -> Unit) {
        Log.d("TakePhotoUiUseCase", "takePhoto")
    }
}
