package cz.vvoleman.phr.common.presentation.viewmodel.healthcare

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilityByIdRepository
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalFacilityPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.mapper.healthcare.SpecificMedicalWorkerPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.core.SpecificMedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.healthcare.detailMedicalWorker.DetailMedicalWorkerDestination
import cz.vvoleman.phr.common.presentation.model.healthcare.detailMedicalWorker.DetailMedicalWorkerNotification
import cz.vvoleman.phr.common.presentation.model.healthcare.detailMedicalWorker.DetailMedicalWorkerViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMedicalWorkerViewModel @Inject constructor(
    private val getSpecificMedicalWorkersRepository: GetSpecificMedicalWorkersRepository,
    private val getFacilityByIdRepository: GetFacilityByIdRepository,
    private val specificWorkerMapper: SpecificMedicalWorkerPresentationModelToDomainMapper,
    private val facilityMapper: MedicalFacilityPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) :
    BaseViewModel<DetailMedicalWorkerViewState, DetailMedicalWorkerNotification>(
        savedStateHandle,
        useCaseExecutorProvider
    ) {

    override val TAG = "DetailMedicalWorkerViewModel"

    override suspend fun initState(): DetailMedicalWorkerViewState {
        val workerId = getWorkerId()
        val specificWorkers = getSpecificWorkers(workerId)
        val medicalWorker = getMedicalWorker(specificWorkers)
        val facilities = getFacilities(specificWorkers)

        return DetailMedicalWorkerViewState(
            specificWorkers = specificWorkers,
            medicalWorker = medicalWorker,
            facilities = facilities
        )
    }

    fun onEdit() {
        val workerId = currentViewState.medicalWorker.id!!
        navigateTo(DetailMedicalWorkerDestination.Edit(workerId))
    }

    private suspend fun getSpecificWorkers(workerId: String): List<SpecificMedicalWorkerPresentationModel> {
        return getSpecificMedicalWorkersRepository
            .getSpecificMedicalWorkers(workerId)
            .map { specificWorkerMapper.toPresentation(it) }
    }

    private fun getMedicalWorker(
        specifics: List<SpecificMedicalWorkerPresentationModel>
    ): MedicalWorkerPresentationModel {
        val medicalWorker = specifics.firstOrNull()?.medicalWorker
        requireNotNull(medicalWorker) { "Medical Worker have no specifics" }

        return medicalWorker
    }

    private fun getWorkerId(): String {
        val workerId = savedStateHandle.get<String>("medicalWorkerId")
        requireNotNull(workerId) { "Medical worker id is null" }

        return workerId
    }

    private suspend fun getFacilities(
        workers: List<SpecificMedicalWorkerPresentationModel>
    ): List<MedicalFacilityPresentationModel> {
        val ids = workers.mapNotNull { it.medicalService?.medicalFacilityId }.distinct()

        return getFacilityByIdRepository
            .getFacilityById(ids)
            .map { facilityMapper.toPresentation(it) }
    }
}
