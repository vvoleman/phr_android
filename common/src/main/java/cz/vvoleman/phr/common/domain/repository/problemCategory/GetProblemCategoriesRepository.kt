package cz.vvoleman.phr.common.domain.repository.problemCategory

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

interface GetProblemCategoriesRepository {

    suspend fun getProblemCategories(patientId: String): List<ProblemCategoryDomainModel>

}
