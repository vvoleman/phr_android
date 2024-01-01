package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordAssetsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalRecordsRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteMedicalWorkersRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete.DeleteProblemCategoriesRepository

class DeletePatientUseCase(
    private val deleteMedicalRecordAssetsRepository: DeleteMedicalRecordAssetsRepository,
    private val deleteMedicalRecordsRepository: DeleteMedicalRecordsRepository,
    private val deleteMedicalWorkersRepository: DeleteMedicalWorkersRepository,
    private val deleteProblemCategoriesRepository: DeleteProblemCategoriesRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<PatientDomainModel, Unit>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: PatientDomainModel) {
        deleteMedicalRecordAssetsRepository.deleteMedicalRecordAssets(request.id)
        deleteMedicalWorkersRepository.deleteMedicalWorkers(request.id)
        deleteProblemCategoriesRepository.deleteProblemCategories(request.id)
        deleteMedicalRecordsRepository.deleteMedicalRecords(request.id)
    }
}
