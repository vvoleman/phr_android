package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel

interface UpdateMedicalRecordProblemCategoryRepository {

    suspend fun updateMedicalRecordProblemCategory(
        medicalRecord: MedicalRecordDomainModel,
        problemCategory: ProblemCategoryDomainModel
    )
}
