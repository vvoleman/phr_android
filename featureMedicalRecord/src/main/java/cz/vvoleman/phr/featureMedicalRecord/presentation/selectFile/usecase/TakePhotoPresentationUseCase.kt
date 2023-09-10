package cz.vvoleman.phr.featureMedicalRecord.presentation.selectFile.usecase

import com.google.mlkit.vision.common.InputImage

interface TakePhotoPresentationUseCase {

    fun takePhoto(onSuccess: (InputImage) -> Unit)
}
