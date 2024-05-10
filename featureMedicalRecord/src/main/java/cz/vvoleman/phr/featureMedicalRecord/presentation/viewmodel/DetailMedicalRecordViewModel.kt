package cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.common.domain.repository.healthcare.GetFacilityByIdRepository
import cz.vvoleman.phr.common.presentation.mapper.healthcare.MedicalFacilityPresentationModelToDomainMapper
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetRecordByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.core.MedicalRecordPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordDestination
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMedicalRecordViewModel @Inject constructor(
    private val getMedicalRecordByIdRepository: GetRecordByIdRepository,
    private val getMedicalFacilityByIdRepository: GetFacilityByIdRepository,
    private val medicalRecordMapper: MedicalRecordPresentationModelToDomainMapper,
    private val facilityMapper: MedicalFacilityPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<DetailMedicalRecordViewState, DetailMedicalRecordNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "DetailMedicalRecordViewModel"

    override suspend fun initState(): DetailMedicalRecordViewState {
        val medicalRecord = getMedicalRecord()
        val facility = getFacility(medicalRecord)

        return DetailMedicalRecordViewState(
            medicalRecord = medicalRecord,
            medicalFacility = facility
        )
    }

    fun onGalleryOpen(assetId: String) {
        navigateTo(DetailMedicalRecordDestination.OpenGallery(
            medicalRecordId = currentViewState.medicalRecord.id,
            assetId = assetId
        ))
    }

    private suspend fun getMedicalRecord(): MedicalRecordPresentationModel {
        val id = savedStateHandle.get<String>("medicalRecordId")
        requireNotNull(id) { "Medical record id is null" }

        val record = getMedicalRecordByIdRepository.getRecordById(id)
        requireNotNull(record) { "Medical record is null" }

        return medicalRecordMapper.toPresentation(record)
    }

    private suspend fun getFacility(medicalRecord: MedicalRecordPresentationModel): MedicalFacilityPresentationModel? {
        val facilityId = medicalRecord.specificMedicalWorker?.medicalService?.medicalFacilityId ?: return null

        val facility = getMedicalFacilityByIdRepository.getFacilityById(facilityId)

        return facility?.let { facilityMapper.toPresentation(it) }
    }
}
