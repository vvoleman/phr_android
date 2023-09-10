package cz.vvoleman.phr.featureMedicalRecord.domain.repository.selectFile

import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel

interface GetDiagnosesByIdsRepository {

    suspend fun getDiagnosesByIds(ids: List<String>): List<DiagnoseDomainModel>
}
