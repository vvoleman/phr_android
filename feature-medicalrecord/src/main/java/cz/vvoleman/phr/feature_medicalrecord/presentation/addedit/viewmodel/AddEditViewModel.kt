package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.GetSelectedPatientUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.SearchRequestDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.UserListsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.SaveFileRequestDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.SelectedObjectsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.AddEditMedicalRecordUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetRecordByIdUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.GetUserListsUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.add_edit.SearchDiagnoseUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.GetDataForSelectedOptionsUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file.SaveMedicalRecordFileUseCase
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.AddEditPresentationModelToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.mapper.DiagnoseDomainModelToPresentationMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model.*
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.mapper.SelectedOptionsPresentationToDomainMapper
import cz.vvoleman.phr.feature_medicalrecord.presentation.select_file.model.SelectedOptionsPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
@Suppress("TooManyFunctions")
class AddEditViewModel @Inject constructor(
    private val addEditMedicalRecordUseCase: AddEditMedicalRecordUseCase,
    private val getRecordByIdUseCase: GetRecordByIdUseCase,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val saveMedicalRecordFileUseCase: SaveMedicalRecordFileUseCase,
    private val searchDiagnoseUseCase: SearchDiagnoseUseCase,
    private val getDataForSelectedOptionsUseCase: GetDataForSelectedOptionsUseCase,
    private val getUserListsUseCase: GetUserListsUseCase,
    private val selectedOptionsPresentationToDomainMapper: SelectedOptionsPresentationToDomainMapper,
    private val diagnoseDomainModelToPresentationMapper: DiagnoseDomainModelToPresentationMapper,
    private val addEditPresentationModelToDomainMapper: AddEditPresentationModelToDomainMapper,
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

        return AddEditViewState(visitDate = LocalDate.now())
    }

    override fun onInit() {
        super.onInit()
        viewModelScope.launch {
            loadSelectedPatient()

            val recordId = savedStateHandle.get<String>("id")
            if (recordId != null) {
                Log.d(TAG, "onInit: recordId: $recordId")
                getRecordByIdUseCase.execute(
                    recordId,
                    ::handleRecordInit
                )
            }

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

            val fileAsset = savedStateHandle.get<AssetPresentationModel>("fileAsset")
            if (fileAsset != null) {
                addFileThumbnail(fileAsset)
            }

            viewModelScope.launch {
                val data = getUserListsUseCase.execute(
                    currentViewState.patientId!!,
                    ::handleUserLists
                )
            }
        }
    }

    fun onAddNewFile() {
        navigateTo(AddEditDestination.AddRecordFile(currentViewState))
    }

    fun onDiagnoseSelected(diagnoseId: String) {
        updateViewState(currentViewState.copy(diagnoseId = diagnoseId))
    }

    fun onDateSelected(date: LocalDate) {
        updateViewState(currentViewState.copy(visitDate = date))
    }

    fun onDiagnoseSearch(query: String) = viewModelScope.launch {
        val data = searchDiagnoseUseCase.execute(
            SearchRequestDomainModel(
                query,
                currentViewState.diagnosePage
            ),
            ::handleDiagnoseSearch
        )
    }

    suspend fun onSubmit() {
        // Check if all required fields are filled
        if (currentViewState.patientId == null) {
            notify(AddEditNotification.PatientNotSelected)
            return
        }

        val record = AddEditPresentationModel(
            recordId = currentViewState.recordId,
            patientId = currentViewState.patientId!!,
            diagnoseId = currentViewState.diagnoseId,
            problemCategoryId = currentViewState.problemCategoryId,
            visitDate = currentViewState.visitDate!!,
            medicalWorkerId = currentViewState.medicalWorkerId,
            assets = currentViewState.assets
        )

        val domainModel = addEditPresentationModelToDomainMapper.toDomain(record)
        updateViewState(currentViewState.copy(saving = true))
        addEditMedicalRecordUseCase.execute(
            domainModel,
            ::handleAddEditResult
        )
    }

    private fun handleAddEditResult(id: String) = viewModelScope.launch {
        updateViewState(currentViewState.copy(saving = false))

        // Save files
        val files = currentViewState.assets.filter { it.id == null }
        if (files.isNotEmpty()) {
            for (file in files) {
                saveMedicalRecordFileUseCase.execute(
                    SaveFileRequestDomainModel(
                        uri = file.uri,
                        medicalRecordId = id
                    )
                ) {}
            }
        }

        notify(AddEditNotification.Success)
        navigateTo(AddEditDestination.RecordSaved(id))
    }

    private fun handleRecordInit(data: MedicalRecordDomainModel?) {
        if (data == null) {
            Log.e(TAG, "handleRecordInit: edit ID given but no record found")
            return
        }

        updateViewState(
            currentViewState.copy(
                recordId = data.id,
                diagnoseId = data.diagnose?.id,
                problemCategoryId = data.problemCategory?.id,
                medicalWorkerId = data.medicalWorker?.id,
                visitDate = data.visitDate,
                assets = data.assets.map { AssetPresentationModel(id = it.id, uri = it.url, createdAt = it.createdAt) }
            )
        )
        Log.d(TAG, "handleRecordInit: $data")
    }

    private fun handleUserLists(data: UserListsDomainModel) {
        Log.d(TAG, "handleUserLists: $data")
        updateViewState(
            currentViewState.copy(
                allMedicalWorkers = data.medicalWorkers,
                allProblemCategories = data.problemCategories
            )
        )
    }

    private fun handleDiagnoseSearch(data: List<DiagnoseDomainModel>) {
        val list = data.map { diagnoseDomainModelToPresentationMapper.toPresentation(it) }
        updateViewState(currentViewState.copy(diagnoseSpinnerList = list))
    }

    fun onDeleteFile(asset: AssetPresentationModel) {
        updateViewState(currentViewState.copy(assets = currentViewState.assets - asset))
        Log.d(TAG, "onDeleteFile: ${currentViewState.assets}")
    }

    private fun addFileThumbnail(asset: AssetPresentationModel) {
        if (!currentViewState.canAddMoreFiles()) {
            notify(AddEditNotification.LimitFilesReached)
            return
        }

        updateViewState(currentViewState.copy(assets = currentViewState.assets + asset))
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

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patientId = patient.id))
    }
}
