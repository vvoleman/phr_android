package cz.vvoleman.phr.common.domain.usecase.problemCategory

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.common.domain.model.problemCategory.request.SaveProblemCategoryRequest
import cz.vvoleman.phr.common.domain.repository.problemCategory.SaveProblemCategoryRepository

class SaveProblemCategoryUseCase(
    private val saveProblemCategoryRepository: SaveProblemCategoryRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<SaveProblemCategoryRequest, String>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: SaveProblemCategoryRequest): String {
        return saveProblemCategoryRepository.saveProblemCategory(request)
    }
}
