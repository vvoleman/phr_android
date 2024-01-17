package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.UserListsDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetProblemCategoriesForPatientRepository

class GetUserListsUseCase(
    private val getProblemCategoriesForPatientRepository: GetProblemCategoriesForPatientRepository,
    private val getSpecificMedicalWorkersRepository: GetSpecificMedicalWorkersRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, UserListsDomainModel>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): UserListsDomainModel {
        val problemCategories = getProblemCategoriesForPatientRepository.getProblemCategoriesForPatient(request)
        val medicalWorkers = getSpecificMedicalWorkersRepository.getSpecificMedicalWorkersByPatient(request)
        return UserListsDomainModel(problemCategories, medicalWorkers)
    }
}
