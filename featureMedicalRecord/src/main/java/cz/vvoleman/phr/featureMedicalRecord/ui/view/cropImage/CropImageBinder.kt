package cz.vvoleman.phr.featureMedicalRecord.ui.view.cropImage

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentCropImageBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.cropImage.CropImageViewState
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.model.Quad

class CropImageBinder :
    BaseViewStateBinder<CropImageViewState, FragmentCropImageBinding, CropImageBinder.Notification>() {

    override fun firstBind(viewBinding: FragmentCropImageBinding, viewState: CropImageViewState) {
        super.firstBind(viewBinding, viewState)

        val uri = viewState.originalUri
        val image = InputImage.fromFilePath(fragmentContext, uri)
        image.bitmapInternal?.let { viewBinding.imageView.setImage(it) }

        viewBinding.buttonCrop.setOnClickListener {
            notify(
                Notification.Crop(
                    viewBinding.imageView.corners,
                    InputImage.fromFilePath(fragmentContext, viewState.originalUri).bitmapInternal!!
                )
            )
        }
    }

    sealed class Notification {
        data class Crop(val quad: Quad, val bitmap: Bitmap) : Notification()
    }

}
