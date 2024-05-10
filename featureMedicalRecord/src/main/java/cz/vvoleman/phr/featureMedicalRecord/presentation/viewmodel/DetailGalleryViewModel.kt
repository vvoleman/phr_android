package cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetRecordByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.core.MedicalRecordPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detailGallery.DetailGalleryNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detailGallery.DetailGalleryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailGalleryViewModel @Inject constructor(
    private val getMedicalRecordByIdRepository: GetRecordByIdRepository,
    private val medicalRecordMapper: MedicalRecordPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider,
) : BaseViewModel<DetailGalleryViewState, DetailGalleryNotification>(savedStateHandle, useCaseExecutorProvider) {

    override val TAG = "DetailGalleryViewModel"

    override suspend fun initState(): DetailGalleryViewState {
        val medicalRecord = getMedicalRecord()
        val assetId = getAssetId(medicalRecord)

        return DetailGalleryViewState(
            medicalRecord = medicalRecord,
            selectedAssetId = assetId,
        )
    }

    private suspend fun getMedicalRecord(): MedicalRecordPresentationModel {
        val id = savedStateHandle.get<String>("medicalRecordId")
        requireNotNull(id) { "Medical record id is required" }

        val medicalRecord = getMedicalRecordByIdRepository.getRecordById(id)
        requireNotNull(medicalRecord) { "Medical record not found" }

        return medicalRecordMapper.toPresentation(medicalRecord)
    }

    private fun getAssetId(medicalRecord: MedicalRecordPresentationModel): String {
        val assetId = savedStateHandle.get<String>("assetId") ?: return medicalRecord.assets.first().id

        if (!medicalRecord.assets.any { it.id == assetId }) {
            require(false) { "Asset not found" }
        }
        return assetId
    }
}
