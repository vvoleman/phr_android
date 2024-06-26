package cz.vvoleman.phr.featureMedicalRecord.domain.usecase.selectFile

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.repository.patient.GetPatientByIdRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.SelectedObjectsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.selectFile.SelectedOptionsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetDiagnoseByIdRepository

class GetDataForSelectedOptionsUseCase(
    private val getDiagnoseByIdRepository: GetDiagnoseByIdRepository,
    private val getPatientByIdRepository: GetPatientByIdRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SelectedOptionsDomainModel, SelectedObjectsDomainModel>(
    coroutineContextProvider
) {

    override suspend fun executeInBackground(request: SelectedOptionsDomainModel): SelectedObjectsDomainModel {
        val patient = request.patientId?.let { getPatientByIdRepository.getById(it) }
        val diagnose = request.diagnoseId?.let { getDiagnoseByIdRepository.getDiagnoseById(it) }
        val visitDate = request.visitDate

        return SelectedObjectsDomainModel(patient, diagnose, visitDate)
    }
}
