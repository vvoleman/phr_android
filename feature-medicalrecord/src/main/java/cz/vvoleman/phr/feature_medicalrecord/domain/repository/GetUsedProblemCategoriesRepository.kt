package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel

interface GetUsedProblemCategoriesRepository{
    suspend fun getUsedProblemCategories(patientId: String): List<ProblemCategoryDomainModel>
}