package cz.vvoleman.phr.common.domain.repository

import cz.vvoleman.phr.common.domain.model.PatientDomainModel

interface GetAllPatientsRepository {

    suspend fun getAll(): List<PatientDomainModel>
}
