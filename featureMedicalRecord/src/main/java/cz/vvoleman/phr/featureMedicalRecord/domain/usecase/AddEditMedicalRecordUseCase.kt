package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.AddEditDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.AddEditMedicalRecordRepository

class AddEditMedicalRecordUseCase(
    private val addEditMedicalRecordRepository: AddEditMedicalRecordRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<AddEditDomainModel, String>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: AddEditDomainModel): String {
        return addEditMedicalRecordRepository.save(request)
    }
}
