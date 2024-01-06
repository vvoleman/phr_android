package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.DeleteMedicalRecordRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordAssetsRepository

class DeleteMedicalRecordUseCase(
    private val deleteMedicalRecord: DeleteMedicalRecordRepository,
    private val deleteMedicalRecordAssetsRepository: DeleteMedicalRecordAssetsRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<MedicalRecordDomainModel, Boolean>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: MedicalRecordDomainModel): Boolean {
        deleteMedicalRecordAssetsRepository.deleteMedicalRecordAssets(request.id)
        deleteMedicalRecord.deleteMedicalRecord(request.id)

        return true
    }
}
