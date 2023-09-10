package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.ProblemCategoryDomainModel

interface GetProblemCategoriesForPatientRepository {

    suspend fun getProblemCategoriesForPatient(patientId: String): List<ProblemCategoryDomainModel>
}
