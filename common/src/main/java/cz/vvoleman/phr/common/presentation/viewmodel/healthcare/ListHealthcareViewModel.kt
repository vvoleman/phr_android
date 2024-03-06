package cz.vvoleman.phr.common.presentation.viewmodel.healthcare

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.healthcare.request.GetMedicalFacilitiesRequest
import cz.vvoleman.phr.common.domain.model.healthcare.request.GetMedicalWorkersRequest
import cz.vvoleman.phr.common.domain.usecase.healthcare.DeleteMedicalWorkerUseCase
import cz.vvoleman.phr.common.domain.usecase.healthcare.GetMedicalFacilitiesUseCase
import cz.vvoleman.phr.common.domain.usecase.healthcare.GetMedicalWorkersUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalFacilityAdditionInfoPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalWorkerAdditionalInfoPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalWorkerPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.core.AdditionalInfoPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareDestination
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.list.ListHealthcareViewState
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListHealthcareViewModel @Inject constructor(
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val getMedicalWorkersUseCase: GetMedicalWorkersUseCase,
    private val getMedicalFacilitiesUseCase: GetMedicalFacilitiesUseCase,
    private val deleteMedicalWorkerUseCase: DeleteMedicalWorkerUseCase,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val workerAdditionalMapper: MedicalWorkerAdditionalInfoPresentationModelToDomainMapper,
    private val facilityAdditionalMapper: MedicalFacilityAdditionInfoPresentationModelToDomainMapper,
    private val workerMapper: MedicalWorkerPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ListHealthcareViewState, ListHealthcareNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "ListHealthcareViewModel"

    override suspend fun initState(): ListHealthcareViewState {
        val patient = getSelectedPatient()
        return ListHealthcareViewState(
            patient = patient,
        )
    }

    override suspend fun onInit() {
        super.onInit()

        viewModelScope.launch {
            val patient = currentViewState.patient ?: return@launch
            val workers = getWorkers(patient.id)
            val facilities = getFacilities(patient.id)

            updateViewState(
                currentViewState.copy(
                    medicalWorkers = workers,
                    medicalFacilities = facilities
                )
            )
        }
    }

    fun onAddWorker() {
        navigateTo(ListHealthcareDestination.AddMedicalWorker)
    }

    private suspend fun getWorkers(
        patientId: String
    ): Map<MedicalWorkerPresentationModel, List<AdditionalInfoPresentationModel<MedicalWorkerPresentationModel>>> {
        val request = GetMedicalWorkersRequest(patientId)

        val result = getMedicalWorkersUseCase.executeInBackground(request)
        return workerAdditionalMapper.toPresentation(result)
    }

    private suspend fun getFacilities(
        patientId: String
    ): Map<MedicalFacilityPresentationModel, List<AdditionalInfoPresentationModel<MedicalFacilityPresentationModel>>> {
        val request = GetMedicalFacilitiesRequest(patientId)

        val result = getMedicalFacilitiesUseCase.executeInBackground(request)
        return facilityAdditionalMapper.toPresentation(result)
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }

    fun onDeleteWorker(item: MedicalWorkerPresentationModel) = viewModelScope.launch {
        deleteMedicalWorkerUseCase.execute(workerMapper.toDomain(item)) {
            updateViewState(
                currentViewState.copy(
                    medicalWorkers = currentViewState.medicalWorkers?.filter { it.key.id != item.id }
                )
            )
        }
    }

    fun onEditWorker(item: MedicalWorkerPresentationModel) {
        navigateTo(ListHealthcareDestination.EditMedicalWorker(item.id!!))
    }

    fun onDetailMedicalWorker(model: MedicalWorkerPresentationModel) {
        navigateTo(ListHealthcareDestination.DetailMedicalWorker(model.id!!))
    }
}
