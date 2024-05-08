package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.repository.healthcare.GetSpecificMedicalWorkersRepository
import cz.vvoleman.phr.common.domain.repository.problemCategory.GetProblemCategoriesRepository
import cz.vvoleman.phr.featureMedicalRecord.domain.model.addEdit.UserListsDomainModel

class GetUserListsUseCase(
    private val getProblemCategoriesRepository: GetProblemCategoriesRepository,
    private val getSpecificMedicalWorkersRepository: GetSpecificMedicalWorkersRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, UserListsDomainModel>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): UserListsDomainModel {
        val problemCategories = getProblemCategoriesRepository.getProblemCategories(request)
        val medicalWorkers = getSpecificMedicalWorkersRepository.getSpecificMedicalWorkersByPatient(request)
        return UserListsDomainModel(problemCategories, medicalWorkers)
    }
}
