package cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordAssetDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.GetTextFromInputImageResultDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.SelectedOptionsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.TextDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.GetRecognizedOptionsFromTextUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.GetTextFromInputImageUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AddEditViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.AssetPresentationModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.mapper.RecognizedOptionsDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectFileDestination
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectFileNotification
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectFileViewState
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectedOptionsPresentationModel
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.usecase.TakePhotoPresentationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SelectFileViewModel @Inject constructor(
    private val getTextFromInputImageUseCase: GetTextFromInputImageUseCase,
    private val getRecognizedOptionsFromTextUseCase: GetRecognizedOptionsFromTextUseCase,
    private val recognizedOptionsDomainModelToPresentationMapper: RecognizedOptionsDomainModelToPresentationMapper,
    private val savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<SelectFileViewState, SelectFileNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "SelectFileViewModel"
;
    override fun initState(): SelectFileViewState {
        return SelectFileViewState()
    }

    fun onRunImageAnalyze(inputImage: InputImage, uri: Uri) {
        setLoading(true)
        val bitmap = inputImage.bitmapInternal
        val asset = AssetPresentationModel(uri = uri.toString())
        updateViewState(currentViewState.copy(previewUri = bitmap, asset = asset))
        runRecognizer(inputImage)
    }

    fun onCancel() {
        navigateTo(SelectFileDestination.Cancel)
    }

    fun onConfirmWithOptions(diagnose: String?, visitDate: String?, patient: String?) {
        val selected = SelectedOptionsPresentationModel(
            diagnoseId = diagnose,
            visitDate = LocalDate.parse(visitDate),
            patientId = patient,
        )

        updateViewState(currentViewState.copy(selectedOptions = selected))
        finish()
    }

    fun onConfirmWithoutOptions() {
        finish()
    }

    private fun finish() {
        val parentViewState = savedStateHandle.get<AddEditViewState>("parentViewState")
            ?: throw IllegalStateException("Parent view state not found")

        if (currentViewState.selectedOptions != null) {
            navigateTo(SelectFileDestination.SuccessWithOptions(parentViewState, currentViewState.selectedOptions!!, currentViewState.asset!!))
        } else {
            navigateTo(SelectFileDestination.Success(parentViewState, currentViewState.asset!!))
        }
    }

    fun onStartSelectingOptions() {
        if (currentViewState.recognizedOptions == null) {
            notify(SelectFileNotification.Error)
        } else {
            notify(SelectFileNotification.OptionsRecognized)
        }
    }

    private fun setLoading(loading: Boolean) {
        var newValue: LocalDate? = null
        if (loading) {
            newValue = LocalDate.now()
        }

        updateViewState(currentViewState.copy(startedAt = newValue))
    }

    /**
     * Runs the recognizer on the image
     */
    private fun runRecognizer(image: InputImage) = viewModelScope.launch {
        getTextFromInputImageUseCase.execute(image) { text ->
            Log.d(TAG, "Got text from image: $text")
            when (text) {
                is GetTextFromInputImageResultDomainModel.Success -> {
                    recognizeOptions(text.text)
                }
                is GetTextFromInputImageResultDomainModel.Failure -> {
                    setLoading(false)
                    notify(SelectFileNotification.Error)
                }
            }
        }
    }

    /**
     * Recognizes options from text
     */
    private fun recognizeOptions(text: TextDomainModel) = viewModelScope.launch {
        setLoading(false)
        getRecognizedOptionsFromTextUseCase.execute(text) { options ->
            val presentationOptions = recognizedOptionsDomainModelToPresentationMapper.toPresentation(options)
            Log.d(TAG, "Got recognized options: $presentationOptions")
            updateViewState(currentViewState.copy(recognizedOptions = presentationOptions))
            notify(SelectFileNotification.OptionsRecognized)
        }
    }

}