package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel

interface GetMedicalRecordByProblemCategoryRepository {

    suspend fun getMedicalRecordByProblemCategory(
        problemCategoryId: String,
    ): List<MedicalRecordDomainModel>

    suspend fun getMedicalRecordByProblemCategory(
        problemCategoryIds: List<String>,
    ): Map<String, List<MedicalRecordDomainModel>>
}
