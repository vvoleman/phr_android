package cz.vvoleman.phr.featureMedicalRecord.domain.repository.patientDelete

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel

interface DeleteMedicalRecordAssetsRepository {

    suspend fun deleteMedicalRecordAssets(patientId: String)

    suspend fun deleteMedicalRecordAssets(medicalRecord: MedicalRecordDomainModel)
}
