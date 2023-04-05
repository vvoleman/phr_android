package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.usecase

import android.net.Uri
import com.google.mlkit.vision.common.InputImage

interface TakePhotoPresentationUseCase {

    fun takePhoto(onSuccess: (InputImage) -> Unit)

}