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
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilitiesPagingStreamRepository
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMedicalWorkerViewModel @Inject constructor(
    private val getFacilitiesPagingStreamRepository: GetFacilitiesPagingStreamRepository,
    private val getSelectedPatientUseCase: GetSelectedPatientUseCase,
    private val saveMedicalWorkerUseCase: SaveMedicalWorkerUseCase,
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

    override fun onInit() {
        super.onInit()

        viewModelScope.launch {
            loadSelectedPatient()
        }
    }

    override fun initState(): AddEditMedicalWorkerViewState {
        return AddEditMedicalWorkerViewState(
            workerId = savedStateHandle.get<String>("medicalWorkerId"),
            details = listOf(
                AddEditMedicalServiceItemPresentationModel(
                    id = "${System.currentTimeMillis()}",
                )
            )
        )
    }

    private suspend fun loadSelectedPatient() {
        val patient = getSelectedPatientUseCase.execute(null).first()
        updateViewState(currentViewState.copy(patient = patientMapper.toPresentation(patient)))
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

    fun onItemDelete(position: Int) {
        val updatedList = currentViewState.details.toMutableList()
        updatedList.removeAt(position)

        updateViewState(
            currentViewState.copy(
                details = updatedList
            )
        )
    }

    fun onItemUndo(item: AddEditMedicalServiceItemPresentationModel, position: Int) {
        val updatedList = currentViewState.details.toMutableList()
        updatedList.add(position, item)

        updateViewState(
            currentViewState.copy(
                details = updatedList
            )
        )
    }

    fun onSave() {
        val missingFields = currentViewState.missingFields

        if (missingFields.isNotEmpty()) {
            notify(AddEditMedicalWorkerNotification.MissingFields(missingFields))
            return
        }

        Log.d(TAG, "onSave: ${currentViewState}")


        val request = SaveMedicalWorkerRequest(
            id = currentViewState.workerId,
            name = currentViewState.name,
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
}
