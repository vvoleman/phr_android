package cz.vvoleman.phr.common.domain.repository.problemCategory

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

interface GetDefaultProblemCategoryRepository {

    suspend fun getDefaultProblemCategory(patientId: String): ProblemCategoryDomainModel?
}
