package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

interface GetProblemCategoriesForPatientRepository {

    suspend fun getProblemCategoriesForPatient(patientId: String): List<ProblemCategoryDomainModel>
}
