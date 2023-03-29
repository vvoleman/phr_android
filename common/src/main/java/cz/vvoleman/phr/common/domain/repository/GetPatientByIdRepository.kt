package cz.vvoleman.phr.common.domain.repository

import cz.vvoleman.phr.common.domain.model.PatientDomainModel

interface GetPatientByIdRepository {

    suspend fun getById(id: String): PatientDomainModel?

}