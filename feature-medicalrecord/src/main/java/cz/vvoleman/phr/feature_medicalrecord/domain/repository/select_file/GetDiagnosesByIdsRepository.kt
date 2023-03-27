package cz.vvoleman.phr.feature_medicalrecord.domain.repository.select_file

import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel

interface GetDiagnosesByIdsRepository {

    suspend fun getDiagnosesByIds(ids: List<String>): List<DiagnoseDomainModel>

}