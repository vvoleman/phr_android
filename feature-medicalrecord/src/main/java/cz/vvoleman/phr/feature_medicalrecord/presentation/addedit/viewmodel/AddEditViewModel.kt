package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.SearchRequestDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.SelectedObjectsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.add_edit.SearchDiagnoseUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.GetDataForSelectedOptionsUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.DiagnoseDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.PatientDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.*
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.mapper.SelectedOptionsPresentationToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectedOptionsPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val searchDiagnoseUseCase: SearchDiagnoseUseCase,
    private val getDataForSelectedOptionsUseCase: GetDataForSelectedOptionsUseCase,
    private val selectedOptionsPresentationToDomainMapper: SelectedOptionsPresentationToDomainMapper,
    private val diagnoseDomainModelToPresentationMapper: DiagnoseDomainModelToPresentationMapper,
    private val savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditViewState, AddEditNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "AddEditViewModel"

    override fun initState(): AddEditViewState {
        val previousViewState = savedStateHandle.get<AddEditViewState>("previousViewState")

        if (previousViewState != null) {
            return previousViewState
        }

        return AddEditViewState()
    }

    override fun onInit() {
        super.onInit()
        loadSelectedPatient()

        // Get param
        val selectedOptions =
            savedStateHandle.get<SelectedOptionsPresentationModel>("selectedOptions")
        if (selectedOptions != null) {
            val options = selectedOptionsPresentationToDomainMapper.toDomain(selectedOptions)
            viewModelScope.launch {
                getDataForSelectedOptionsUseCase.execute(
                    options,
                    ::setSelectedOptions
                )
            }
        }

        val fileUri = savedStateHandle.get<Uri>("fileUri")
        if (fileUri != null) {
            addFileThumbnail(fileUri)
        }
    }

    fun onAddNewFile() {
        navigateTo(AddEditDestination.AddRecordFile(currentViewState))
    }

    fun onDiagnoseSelected(diagnoseId: String) {
        updateViewState(currentViewState.copy(diagnoseId = diagnoseId))
    }

    fun onDiagnoseSearch(query: String) = viewModelScope.launch {
        val data = searchDiagnoseUseCase.execute(
            SearchRequestDomainModel(
                query,
                currentViewState.diagnosePage
            ), ::handleDiagnoseSearch
        )
    }

    fun onSubmit(input: AddEditPresentationModel) {

    }

    private fun handleDiagnoseSearch(data: List<DiagnoseDomainModel>) {
        val list = data.map { diagnoseDomainModelToPresentationMapper.toPresentation(it) }
        updateViewState(currentViewState.copy(diagnoseSpinnerList = list))
    }

    fun onDeleteFile(uri: Uri) {
        updateViewState(currentViewState.copy(files = currentViewState.files - uri))
    }

    private fun addFileThumbnail(uri: Uri) {
        if (!currentViewState.canAddMoreFiles()) {
            notify(AddEditNotification.LimitFilesReached)
            return
        }

        updateViewState(currentViewState.copy(files = currentViewState.files + uri))
    }

    private fun setSelectedOptions(options: SelectedObjectsDomainModel) {
        var state = currentViewState.copy()

        var diagnose: String? = null
        if (options.diagnose != null) {
            diagnose = options.diagnose.id
        }

        var patient: String? = null
        if (options.patient != null) {
            patient = options.patient.id
        }

        var visitDate: LocalDate? = null
        if (options.visitDate != null) {
            visitDate = options.visitDate
        }

        diagnose?.let { state = state.copy(diagnoseId = it) }
        patient?.let { state = state.copy(patientId = it) }
        visitDate?.let { state = state.copy(visitDate = it) }

        updateViewState(state)
    }

    private fun loadSelectedPatient() = viewModelScope.launch {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patientId = patient.id))
    }

}