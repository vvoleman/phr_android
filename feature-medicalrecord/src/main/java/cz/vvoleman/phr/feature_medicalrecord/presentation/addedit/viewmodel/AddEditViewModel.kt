package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.SelectedObjectsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.GetDataForSelectedOptionsUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.DiagnoseDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.*
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.mapper.SelectedOptionsPresentationToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectedOptionsPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.http.OPTIONS
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val patientDomainModelToPresentation: PatientDomainModelToPresentationMapper,
    private val getDataForSelectedOptionsUseCase: GetDataForSelectedOptionsUseCase,
    private val selectedOptionsPresentationToDomainMapper: SelectedOptionsPresentationToDomainMapper,
    private val diagnoseDomainModelToPresentationMapper: DiagnoseDomainModelToPresentationMapper,
    private val savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditViewState, AddEditNotification>(savedStateHandle,useCaseExecutorProvider){

    override val TAG = "AddEditViewModel"

    override fun initState(): AddEditViewState {
        return AddEditViewState()
    }

    override fun onInit() {
        super.onInit()
        loadSelectedPatient()

        // Get param
        val selectedOptions = savedStateHandle.get<SelectedOptionsPresentationModel>("selectedOptions")
        if (selectedOptions != null) {
            Log.d(TAG, "Selected options: $selectedOptions")
            val options = selectedOptionsPresentationToDomainMapper.toDomain(selectedOptions)
            viewModelScope.launch {  getDataForSelectedOptionsUseCase.execute(options, ::setSelectedOptions) }
        }

        val fileUri = savedStateHandle.get<Uri>("fileUri")
        if (fileUri != null) {
            Log.d(TAG, "File uri: $fileUri")
            addFileThumbnail(fileUri)
        }
    }

    fun onAddNewFile() {
        navigateTo(AddEditDestination.AddRecordFile)
    }

    fun onSubmit(input: AddEditPresentationModel) {

    }

    fun onDeleteFile(uri: Uri) {
        updateViewState(currentViewState.copy(files = currentViewState.files - uri))
    }

    private fun addFileThumbnail(uri: Uri) {
        Log.d(TAG, "current size: ${currentViewState.files.size}")
        Log.d(TAG, "can add more files: ${currentViewState.canAddMoreFiles()}")
        if (!currentViewState.canAddMoreFiles()) {
            notify(AddEditNotification.LimitFilesReached)
            return
        }

        updateViewState(currentViewState.copy(files = currentViewState.files + uri))
    }

    private fun setSelectedOptions(options: SelectedObjectsDomainModel) {
        Log.d(TAG, "setSelectedOptions: $options")
        var state = currentViewState.copy()

        var diagnose: DiagnosePresentationModel? = null
        if (options.diagnose != null) {
            diagnose = diagnoseDomainModelToPresentationMapper.toPresentation(options.diagnose)
        }

        var patient: PatientPresentationModel? = null
        if (options.patient != null) {
            patient = patientDomainModelToPresentation.toPresentation(options.patient)
        }

        var visitDate: LocalDate? = null
        if (options.visitDate != null) {
            visitDate = options.visitDate
        }

        diagnose?.let { state = state.copy(diagnose = it) }
        patient?.let { state = state.copy(patient = it) }
        visitDate?.let { state = state.copy(visitDate = it) }

        updateViewState(state)
    }

    private fun loadSelectedPatient() = viewModelScope.launch {
        val patient = getSelectedPatientUseCase.execute(null).first()
        Log.d(TAG, "loadSelectedPatient: $patient")
        updateViewState(currentViewState.copy(patient = patientDomainModelToPresentation.toPresentation(patient)))
    }

}