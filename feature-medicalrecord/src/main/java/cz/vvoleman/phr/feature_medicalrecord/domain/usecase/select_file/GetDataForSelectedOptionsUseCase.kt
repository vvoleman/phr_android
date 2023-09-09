package cz.vvoleman.phr.feature_medicalrecord.domain.usecase.select_file

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.repository.GetPatientByIdRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.SelectedObjectsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.SelectedOptionsDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetDiagnoseByIdRepository

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
