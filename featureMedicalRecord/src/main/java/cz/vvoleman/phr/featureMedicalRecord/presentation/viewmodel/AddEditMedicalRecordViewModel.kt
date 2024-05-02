package cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.SpecificMedicalWorkerPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.problemCategory.ProblemCategoryPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.core.SpecificMedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.UserListsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.SaveFileRequestDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.SelectedObjectsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetDiagnoseByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit.DeleteUnusedFilesRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit.GetDiagnosesPagingStreamRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.AddEditMedicalRecordUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetRecordByIdUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.GetUserListsUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.addEdit.SearchDiagnoseUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.GetDataForSelectedOptionsUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile.SaveMedicalRecordFileUseCase
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.addEdit.AddEditPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.addEdit.AddEditViewStateToModelMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.addEdit.DiagnosePresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.selectFile.SelectedOptionsPresentationToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.*
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.selectFile.SelectedOptionsPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
@Suppress("TooManyFunctions")
class AddEditMedicalRecordViewModel @Inject constructor(
    private val addEditMedicalRecordUseCase: AddEditMedicalRecordUseCase,
    private val getRecordByIdUseCase: GetRecordByIdUseCase,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val saveMedicalRecordFileUseCase: SaveMedicalRecordFileUseCase,
    private val deleteUnusedFilesRepository: DeleteUnusedFilesRepository,
    private val searchDiagnoseUseCase: SearchDiagnoseUseCase,
    private val getDataForSelectedOptionsUseCase: GetDataForSelectedOptionsUseCase,
    private val getUserListsUseCase: GetUserListsUseCase,
    private val getDiagnosesPagingStreamRepository: GetDiagnosesPagingStreamRepository,
    private val getDiagnoseByIdRepository: GetDiagnoseByIdRepository,
    private val selectedOptionsPresentationToDomainMapper: SelectedOptionsPresentationToDomainMapper,
    private val diagnoseMapper: DiagnosePresentationModelToDomainMapper,
    private val addEditPresentationModelToDomainMapper: AddEditPresentationModelToDomainMapper,
    private val problemCategoryMapper: ProblemCategoryPresentationModelToDomainMapper,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val viewStateMapper: AddEditViewStateToModelMapper,
    private val specificWorkerMapper: SpecificMedicalWorkerPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<AddEditViewState, AddEditNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "AddEditViewModel"

    override suspend fun initState(): AddEditViewState {
        val previousState = savedStateHandle.get<AddEditPresentationModel>("previousViewState")

        val patient = getSelectedPatient()
        val record = getExistingRecord()
        val userLists = getUserLists(patient.id)
        val selectedObjects = getSelectedOptions()

        val diagnose = selectedObjects?.diagnose?.let { diagnoseMapper.toPresentation(it) } ?: previousState?.diagnose
        val visitDate = selectedObjects?.visitDate ?: previousState?.visitDate

        if (previousState != null) {
            return AddEditViewState(
                recordId = previousState.recordId,
                createdAt = previousState.createdAt,
                diagnose = diagnose,
                specificMedicalWorkerId = previousState.specificMedicalWorker,
                problemCategoryId = previousState.problemCategoryId,
                patientId = previousState.patientId,
                visitDate = visitDate,
                allProblemCategories = userLists.problemCategories.map { problemCategoryMapper.toPresentation(it) },
                allMedicalWorkers = specificWorkerMapper.toPresentation(userLists.medicalWorkers),
                assets = previousState.assets,
            )
        }

        return AddEditViewState(
            visitDate = record?.visitDate ?: LocalDate.now(),
            patientId = patient.id,
            recordId = record?.id,
            diagnose = record?.diagnose?.let { diagnoseMapper.toPresentation(it) },
            problemCategoryId = record?.problemCategory?.id,
            specificMedicalWorkerId = record?.specificMedicalWorker?.id,
            assets = record?.assets?.map { AssetPresentationModel(id = it.id, uri = it.url, createdAt = it.createdAt) }
                ?: listOf(),
            allMedicalWorkers = userLists.medicalWorkers.map { specificWorkerMapper.toPresentation(it) },
            allProblemCategories = userLists.problemCategories.map { problemCategoryMapper.toPresentation(it) }
        )
    }

    override suspend fun onInit() {
        super.onInit()
        viewModelScope.launch {
            // Get param


            val fileAsset = savedStateHandle.get<AssetPresentationModel>("fileAsset")
            if (fileAsset != null) {
                addFileThumbnail(fileAsset)
            }
        }
    }

    fun onAddNewFile() {
        val model = viewStateMapper.toModel(currentViewState)
        navigateTo(AddEditDestination.AddRecordFile(model))
    }

    fun onDiagnoseSelected(diagnose: DiagnosePresentationModel?) {
        updateViewState(currentViewState.copy(diagnose = diagnose))
    }

    fun onDateSelected(date: LocalDate) {
        updateViewState(currentViewState.copy(visitDate = date))
    }

    fun onDiagnoseSearch(query: String): Flow<PagingData<DiagnosePresentationModel>> {
        if (query != currentViewState.query || currentViewState.diagnoseStream == null) {
            updateViewState(currentViewState.copy(query = query))
            val flow = getDiagnosesPagingStreamRepository
                .getDiagnosesPagingStream(query)
                .map { pagingData ->
                    pagingData.map {
                        diagnoseMapper.toPresentation(it)
                    }
                }
                .cachedIn(viewModelScope)

            updateViewState(
                currentViewState.copy(
                    diagnoseStream = flow
                )
            )

            return flow
        }

        return currentViewState.diagnoseStream ?: throw IllegalStateException("Diagnose stream is null")
    }

    suspend fun onSubmit() {
        // Check if all required fields are filled
        if (currentViewState.patientId == null) {
            notify(AddEditNotification.PatientNotSelected)
            return
        }

        val record = viewStateMapper.toModel(currentViewState)

        val domainModel = addEditPresentationModelToDomainMapper.toDomain(record)
        updateViewState(currentViewState.copy(saving = true))
        addEditMedicalRecordUseCase.execute(
            domainModel,
            ::handleAddEditResult
        )
    }

    private fun handleAddEditResult(id: String) = viewModelScope.launch {
        updateViewState(currentViewState.copy(saving = false))

        val previousFiles = currentViewState.assets.filter { it.id != null }.map { it.id!! }
        deleteUnusedFilesRepository.deleteUnusedFiles(id, previousFiles)

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

    private fun setSelectedOptions(options: SelectedObjectsDomainModel) = viewModelScope.launch {
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

        diagnose?.let { state = state.copy(diagnose = getDiagnose(it)) }
        patient?.let { state = state.copy(patientId = it) }
        visitDate?.let { state = state.copy(visitDate = it) }

        updateViewState(state)
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }

    private suspend fun getExistingRecord(): MedicalRecordDomainModel? {
        val recordId = savedStateHandle.get<String>("id") ?: return null

        return getRecordByIdUseCase.executeInBackground(recordId)
    }

    private suspend fun getUserLists(patientId: String): UserListsDomainModel {
        return getUserListsUseCase.executeInBackground(patientId)
    }

    private suspend fun getDiagnose(id: String): DiagnosePresentationModel? {
        return getDiagnoseByIdRepository.getDiagnoseById(id)?.let {
            diagnoseMapper.toPresentation(it)
        }
    }

    private suspend fun getSelectedOptions(): SelectedObjectsDomainModel? {
        val selectedOptions = savedStateHandle.get<SelectedOptionsPresentationModel>("selectedOptions") ?: return null

        val options = selectedOptionsPresentationToDomainMapper.toDomain(selectedOptions)
        return getDataForSelectedOptionsUseCase.executeInBackground(options)
    }

    fun onProblemCategorySelected(name: String?) {
        val problemCategory = currentViewState.allProblemCategories.find { it.name == name }
        updateViewState(currentViewState.copy(problemCategoryId = problemCategory?.id))
    }

    fun onMedicalWorkerSelected(id: SpecificMedicalWorkerPresentationModel?) {
        updateViewState(currentViewState.copy(specificMedicalWorkerId = id?.id))
    }
}
