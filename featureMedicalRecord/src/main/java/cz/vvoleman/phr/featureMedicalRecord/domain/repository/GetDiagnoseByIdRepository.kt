package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel

interface GetDiagnoseByIdRepository {

    suspend fun getDiagnoseById(id: String): DiagnoseDomainModel?
}
