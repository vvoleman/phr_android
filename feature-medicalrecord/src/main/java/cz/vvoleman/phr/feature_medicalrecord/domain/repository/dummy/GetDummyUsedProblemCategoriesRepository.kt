package cz.vvoleman.phr.feature_medicalrecord.domain.repository.dummy

import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedProblemCategoriesRepository

class GetDummyUsedProblemCategoriesRepository : GetUsedProblemCategoriesRepository {

    override suspend fun getUsedProblemCategories(patientId: String): List<ProblemCategoryDomainModel> {
        val allCategories = getDummyData()
        return allCategories.filter { it.patientId == patientId }
    }

    private fun getDummyData(): List<ProblemCategoryDomainModel> {
        return listOf(
            ProblemCategoryDomainModel(
                id = "1",
                name = "Kategorie 1",
                color = "",
                patientId = "1"
            ),
            ProblemCategoryDomainModel(
                id = "2",
                name = "Kategorie 2",
                color = "",
                patientId = "1"
            ),
            ProblemCategoryDomainModel(
                id = "3",
                name = "Kategorie 3",
                color = "",
                patientId = "2"
            ),
            ProblemCategoryDomainModel(
                id = "4",
                name = "Kategorie 4",
                color = "",
                patientId = "2"
            )
        )
    }
}
