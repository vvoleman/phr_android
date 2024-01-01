package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

interface GetUsedProblemCategoriesRepository {
    suspend fun getUsedProblemCategories(patientId: String): List<ProblemCategoryDomainModel>
}
