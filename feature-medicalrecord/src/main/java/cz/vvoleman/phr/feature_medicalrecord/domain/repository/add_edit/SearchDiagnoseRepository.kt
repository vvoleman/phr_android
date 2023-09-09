package cz.vvoleman.phr.feature_medicalrecord.domain.repository.add_edit

import cz.vvoleman.phr.feature_medicalrecord.domain.model.DiagnoseDomainModel

interface SearchDiagnoseRepository {

    suspend fun searchDiagnose(query: String, page: Int): List<DiagnoseDomainModel>
}
