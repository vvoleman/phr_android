package cz.vvoleman.phr.featureMedicalRecord.domain.repository

import cz.vvoleman.phr.featureMedicalRecord.domain.model.MedicalRecordDomainModel

interface GetMedicalRecordByPatientRepository {

    suspend fun getMedicalRecordByPatient(patientId: String): List<MedicalRecordDomainModel>
}
