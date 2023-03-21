package cz.vvoleman.phr.feature_medicalrecord.domain.repository.dummy

import cz.vvoleman.phr.feature_medicalrecord.domain.model.AddressDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalWorkerDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedMedicalWorkersRepository
import cz.vvoleman.phr.feature_medicalrecord.domain.repository.GetUsedProblemCategoriesRepository

class GetDummyUsedRepository : GetUsedProblemCategoriesRepository, GetUsedMedicalWorkersRepository {
    override suspend fun getUsedMedicalWorkers(patientId: String): List<MedicalWorkerDomainModel> {
        val allWorkers = getDummyWorkers()
        return allWorkers.filter { it.patientId == patientId }

    }

    override suspend fun getUsedProblemCategories(patientId: String): List<ProblemCategoryDomainModel> {
        val allCategories = getDummyCategories()
        return allCategories.filter { it.patientId == patientId }
    }

    private fun getDummyWorkers(): List<MedicalWorkerDomainModel> {
        val address = AddressDomainModel(
            city = "Ústí nad Labem",
            street = "Ulice",
            houseNumber = "123",
            zipCode = "40000",
        )
        return listOf(
            MedicalWorkerDomainModel(
                id = "1",
                name = "Jméno 1",
                address = address,
                patientId = "1"
            ),
            MedicalWorkerDomainModel(
                id = "2",
                name = "Jméno 2",
                address = address,
                patientId = "1"
            ),
            MedicalWorkerDomainModel(
                id = "3",
                name = "Jméno 3",
                address = address,
                patientId = "2"
            ),
            MedicalWorkerDomainModel(
                id = "4",
                name = "Jméno 4",
                address = address,
                patientId = "2"
            ),
        )
    }

    private fun getDummyCategories(): List<ProblemCategoryDomainModel> {
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
            ),
        )
    }
}