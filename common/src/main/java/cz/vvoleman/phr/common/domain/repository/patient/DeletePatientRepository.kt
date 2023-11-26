package cz.vvoleman.phr.common.domain.repository.patient

interface DeletePatientRepository {

    suspend fun deletePatient(id: String): Boolean
}
