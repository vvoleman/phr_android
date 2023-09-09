package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel

interface GetDiagnoseByIdRepository {

    suspend fun getDiagnoseById(id: String): DiagnoseDomainModel?
}
