package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.ProblemCategoryDomainModel

interface GetProblemCategoriesForPatientRepository {

    suspend fun getProblemCategoriesForPatient(patientId: String): List<ProblemCategoryDomainModel>
}
