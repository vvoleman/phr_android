package cz.vvoleman.phr.common.domain.repository.problemCategory

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

interface GetProblemCategoryByIdRepository {

    suspend fun getProblemCategoryById(id: String): ProblemCategoryDomainModel?

}
