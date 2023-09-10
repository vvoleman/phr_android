package cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete

interface DeleteProblemCategoriesRepository {

    suspend fun deleteProblemCategories(patientId: String)
}
