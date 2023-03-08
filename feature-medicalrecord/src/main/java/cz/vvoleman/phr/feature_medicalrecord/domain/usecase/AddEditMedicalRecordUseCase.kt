package cz.vvoleman.phr.feature_medicalrecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.AddEditMedicalRecordRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.AddEditMedicalRecordDomainModel

class AddEditMedicalRecordUseCase(
    private val addEditMedicalRecordRepository: AddEditMedicalRecordRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<AddEditMedicalRecordDomainModel, String>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: AddEditMedicalRecordDomainModel): String {
        return addEditMedicalRecordRepository.save(request)
    }
}