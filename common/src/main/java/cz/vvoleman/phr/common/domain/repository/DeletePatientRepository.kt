package cz.vvoleman.phr.common.domain.repository

interface DeletePatientRepository {

    suspend fun deletePatient(id: String): Boolean
}
