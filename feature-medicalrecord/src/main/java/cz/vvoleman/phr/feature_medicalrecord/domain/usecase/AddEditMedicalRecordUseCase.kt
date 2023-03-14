package cz.vvoleman.phr.feature_medicalrecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.feature_medicalrecord.domain.model.add_edit.AddEditDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.AddEditMedicalRecordRepository

class AddEditMedicalRecordUseCase(
    private val addEditMedicalRecordRepository: AddEditMedicalRecordRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<AddEditDomainModel, String>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: AddEditDomainModel): String {
        return addEditMedicalRecordRepository.save(request)
    }
}