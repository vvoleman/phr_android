package cz.vvoleman.phr.common.domain.repository.patient

interface SwitchSelectedPatientRepository {

    suspend fun switchSelectedPatient(patientId: String)
}
