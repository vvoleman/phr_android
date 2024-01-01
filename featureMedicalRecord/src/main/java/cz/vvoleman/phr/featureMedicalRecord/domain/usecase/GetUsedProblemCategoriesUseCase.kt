package cz.vvoleman.phr.featureMedicalRecord.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.repository.GetUsedProblemCategoriesRepository

class GetUsedProblemCategoriesUseCase(
    private val getUsedProblemCategoriesRepository: GetUsedProblemCategoriesRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<String, List<ProblemCategoryDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: String): List<ProblemCategoryDomainModel> {
        return getUsedProblemCategoriesRepository.getUsedProblemCategories(request)
    }
}
