package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.DeleteMedicalRecordRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordAssetsRepository

class DeleteMedicalRecordUseCase(
    private val deleteMedicalRecord: DeleteMedicalRecordRepository,
    private val deleteMedicalRecordAssetsRepository: DeleteMedicalRecordAssetsRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, Boolean>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): Boolean {
        deleteMedicalRecordAssetsRepository.deleteMedicalRecordAssets(request)
        deleteMedicalRecord.deleteMedicalRecord(request)

        return true
    }
}
