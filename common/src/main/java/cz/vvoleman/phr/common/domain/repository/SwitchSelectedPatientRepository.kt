package cz.vvoleman.phr.common.domain.repository

interface SwitchSelectedPatientRepository {

    suspend fun switchSelectedPatient(patientId: String)
}
