package cz.vvoleman.phr.featureMedicalRecord.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.presentation.viewmodel.usecase.UseCaseExecutorProvider
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetRecordByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.presentation.mapper.core.MedicalRecordPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordDestination
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordNotification
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detail.DetailMedicalRecordViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMedicalRecordViewModel @Inject constructor(
    private val getMedicalRecordByIdRepository: GetRecordByIdRepository,
    private val medicalRecordMapper: MedicalRecordPresentationModelToDomainMapper,
    savedStateHandle: SavedStateHandle,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<DetailMedicalRecordViewState, DetailMedicalRecordNotification>(
    savedStateHandle,
    useCaseExecutorProvider
) {

    override val TAG = "DetailMedicalRecordViewModel"

    override suspend fun initState(): DetailMedicalRecordViewState {
        val id = savedStateHandle.get<String>("medicalRecordId")
        val record = id?.let { getMedicalRecordByIdRepository.getRecordById(it) }

        if (record == null) {
            navigateTo(DetailMedicalRecordDestination.NoMedicalRecord(id))
            throw IllegalStateException("Medical record id is null")
        }

        return DetailMedicalRecordViewState(
            medicalRecord = medicalRecordMapper.toPresentation(record)
        )
    }
}
