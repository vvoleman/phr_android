package cz.vvoleman.phr.feature_medicalrecord.domain.repository.patient_delete

interface DeleteProblemCategoriesRepository {

    suspend fun deleteProblemCategories(patientId: String)

}