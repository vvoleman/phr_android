package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordAssetsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordsRepository

class DeletePatientUseCase(
    private val deleteMedicalRecordAssetsRepository: DeleteMedicalRecordAssetsRepository,
    private val deleteMedicalRecordsRepository: DeleteMedicalRecordsRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<PatientDomainModel, Unit>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: PatientDomainModel) {
        deleteMedicalRecordAssetsRepository.deleteMedicalRecordAssets(request.id)
        deleteMedicalRecordsRepository.deleteMedicalRecords(request.id)
    }
}
