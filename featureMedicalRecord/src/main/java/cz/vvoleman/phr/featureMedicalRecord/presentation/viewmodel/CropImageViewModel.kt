package cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AddEditPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.cropImage.CropImageDestination
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.cropImage.CropImageNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.cropImage.CropImageViewState
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.model.Quad
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.utils.ImageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CropImageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<CropImageViewState, CropImageNotification>(
    savedStateHandle, useCaseExecutorProvider
) {

    override val TAG = "CropImageViewModel"

    override suspend fun initState(): CropImageViewState {
        return CropImageViewState(
            parentViewState = getParentViewState(),
            originalUri = getUri()
        )
    }

    private fun getUri(): Uri {
        val uri = savedStateHandle.get<Uri>("uri")
        requireNotNull(uri) { "Bitmap is null" }

        return uri
    }

    private fun getParentViewState(): AddEditPresentationModel {
        val parentViewState = savedStateHandle.get<AddEditPresentationModel>("parentViewState")
        requireNotNull(parentViewState) { "Parent view state is null" }

        return parentViewState
    }

    fun onCropImage(bitmap: Bitmap, quad: Quad) {
        val uri = currentViewState.originalUri

        val result = ImageUtil().crop(uri.path!!, quad, bitmap)

        navigateTo(CropImageDestination.CropImage(currentViewState.parentViewState, result))
    }
}
