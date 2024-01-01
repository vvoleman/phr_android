package cz.vvoleman.phr.common.presentation.viewmodel.healthcare

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.model.healthcare.save.SaveMedicalWorkerRequest
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithServicesDomainModel
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesPagingStreamRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilityByIdRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetMedicalWorkersWithServicesRepository
import cz.vvoleman.phr.common.domain.usecase.healthcare.SaveMedicalWorkerUseCase
import cz.vvoleman.phr.common.domain.usecase.patient.GetSelectedPatientUseCase
import cz.vvoleman.phr.common.presentation.mapper.PatientPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.AddEditMedicalServiceItemPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalFacilityPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalServiceItemPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerDestination
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.addEdit.AddEditMedicalWorkerViewState
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AddEditMedicalWorkerViewModel @Inject constructor(
    private val getFacilitiesPagingStreamRepository: GetFacilitiesPagingStreamRepository,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val saveMedicalWorkerUseCase: SaveMedicalWorkerUseCase,
    private val getFacilityByIdRepository: GetFacilityByIdRepository,
    private val getMedicalWorkersWithServicesRepository: GetMedicalWorkersWithServicesRepository,
    private val patientMapper: PatientPresentationModelToDomainMapper,
    private val facilityMapper: MedicalFacilityPresentationModelToDomainMapper,
    private val addEditMapper: AddEditMedicalServiceItemPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<AddEditMedicalWorkerViewState, AddEditMedicalWorkerNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "AddEditMedicalWorkerViewModel"

    override suspend fun initState(): AddEditMedicalWorkerViewState {
        val patient = getSelectedPatient()
        val workerId = savedStateHandle.get<String>("medicalWorkerId")
        val worker = workerId?.let { getExistingWorker(it) }

        val details = if (worker != null) {
            getExistingDetails(worker)
        } else {
            listOf(
                AddEditMedicalServiceItemPresentationModel(
                    id = "${System.currentTimeMillis()}",
                )
            )
        }

        return AddEditMedicalWorkerViewState(
            workerId = workerId,
            patient = patient,
            details = details,
            name = worker?.medicalWorker?.name
        )
    }

    private suspend fun getSelectedPatient(): PatientPresentationModel {
        val patient = getSelectedPatientUseCase.execute(null).first()
        return patientMapper.toPresentation(patient)
    }

    fun onAddFacility() {
        updateViewState(
            currentViewState.copy(
                details = currentViewState.details + AddEditMedicalServiceItemPresentationModel(
                    id = "${System.currentTimeMillis()}",
                )
            )
        )
    }

    fun onFacilitySearch(query: String): Flow<PagingData<MedicalFacilityPresentationModel>> {
        if (query != currentViewState.query || currentViewState.facilityStream == null) {
            updateViewState(currentViewState.copy(query = query))
            val flow = getFacilitiesPagingStreamRepository
                .getFacilitiesPagingStream(query)
                .map { pagingData ->
                    pagingData.map {
                        facilityMapper.toPresentation(it)
                    }
                }
                .cachedIn(viewModelScope)

            updateViewState(
                currentViewState.copy(
                    facilityStream = flow
                )
            )

            return flow
        }

        return currentViewState.facilityStream ?: throw IllegalStateException("Facility stream is null")
    }

    fun onItemUpdate(item: AddEditMedicalServiceItemPresentationModel, position: Int) {
        Log.d(TAG, "onItemUpdate: $item (Binding..)")
        val updatedList = currentViewState.details.toMutableList()
        updatedList[position] = item

        updateViewState(
            currentViewState.copy(
                details = updatedList
            )
        )
    }

//    fun onItemDelete(position: Int) {
//        val updatedList = currentViewState.details.toMutableList()
//        updatedList.removeAt(position)
//
//        updateViewState(
//            currentViewState.copy(
//                details = updatedList
//            )
//        )
//    }
//
//    fun onItemUndo(item: AddEditMedicalServiceItemPresentationModel, position: Int) {
//        val updatedList = currentViewState.details.toMutableList()
//        updatedList.add(position, item)
//
//        updateViewState(
//            currentViewState.copy(
//                details = updatedList
//            )
//        )
//    }

    fun onSave() {
        val missingFields = currentViewState.missingFields

        if (missingFields.isNotEmpty()) {
            notify(AddEditMedicalWorkerNotification.MissingFields(missingFields))
            return
        }

        Log.d(TAG, "onSave: $currentViewState")

        val request = SaveMedicalWorkerRequest(
            id = currentViewState.workerId,
            name = currentViewState.name!!,
            patientId = currentViewState.patient!!.id,
            medicalServices = currentViewState.details.map { addEditMapper.toDomain(it) }
        )

        execute(
            useCase = saveMedicalWorkerUseCase,
            value = request,
            onSuccess = {
                navigateTo(AddEditMedicalWorkerDestination.Saved(it))
            },
            onException = {
                Log.e(TAG, it.toString())
                notify(AddEditMedicalWorkerNotification.CannotSave)
            }
        )
    }

    fun onNameChanged(name: String) {
        updateViewState(
            currentViewState.copy(
                name = name
            )
        )
    }

    private suspend fun getExistingDetails(
        worker: MedicalWorkerWithServicesDomainModel
    ): List<AddEditMedicalServiceItemPresentationModel> {
        return worker.services.map {
            val facility =
                getFacilityByIdRepository.getFacilityById(it.medicalService.medicalFacilityId) ?: return@map null
            AddEditMedicalServiceItemPresentationModel(
                facility = facilityMapper.toPresentation(facility),
                serviceId = it.medicalService.id,
                telephone = it.telephone,
                email = it.email
            )
        }.filterNotNull().first().let {
            listOf(it)
        }
    }

    private suspend fun getExistingWorker(workerId: String): MedicalWorkerWithServicesDomainModel? {
        return getMedicalWorkersWithServicesRepository.getMedicalWorkerWithServices(workerId)
    }
}
