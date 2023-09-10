package cz.vvoleman.phr.featureMedicalRecord.domain.repository.addEdit

import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel

interface SearchDiagnoseRepository {

    suspend fun searchDiagnose(query: String, page: Int): List<DiagnoseDomainModel>
}
