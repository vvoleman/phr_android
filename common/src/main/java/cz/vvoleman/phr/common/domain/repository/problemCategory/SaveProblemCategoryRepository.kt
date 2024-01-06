package cz.vvoleman.phr.common.domain.repository.problemCategory

import cz.vvoleman.phr.common.domain.model.problemCategory.request.SaveProblemCategoryRequest

interface SaveProblemCategoryRepository {

    suspend fun saveProblemCategory(request: SaveProblemCategoryRequest): String

}
