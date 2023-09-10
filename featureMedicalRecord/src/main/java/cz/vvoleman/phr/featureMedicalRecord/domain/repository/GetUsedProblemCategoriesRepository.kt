package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.ProblemCategoryDomainModel

interface GetUsedProblemCategoriesRepository {
    suspend fun getUsedProblemCategories(patientId: String): List<ProblemCategoryDomainModel>
}
