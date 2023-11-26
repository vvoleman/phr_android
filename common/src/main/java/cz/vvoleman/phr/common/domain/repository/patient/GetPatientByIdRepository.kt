package cz.vvoleman.phr.common.domain.repository.patient

import cz.vvoleman.phr.common.domain.model.PatientDomainModel

interface GetPatientByIdRepository {

    suspend fun getById(id: String): PatientDomainModel?
}
