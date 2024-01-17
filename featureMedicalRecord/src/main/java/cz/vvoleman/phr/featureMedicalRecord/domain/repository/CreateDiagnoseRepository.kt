package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.DiagnoseDomainModel

interface CreateDiagnoseRepository {

    suspend fun createDiagnose(diagnose: DiagnoseDomainModel)

}
