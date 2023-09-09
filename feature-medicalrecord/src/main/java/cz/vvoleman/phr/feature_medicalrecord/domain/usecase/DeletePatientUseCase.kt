package cz.vvoleman.phr.feature_medicalrecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.patient_delete.DeleteMedicalRecordAssetsRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.patient_delete.DeleteMedicalRecordsRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.patient_delete.DeleteMedicalWorkersRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.patient_delete.DeleteProblemCategoriesRepository

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
