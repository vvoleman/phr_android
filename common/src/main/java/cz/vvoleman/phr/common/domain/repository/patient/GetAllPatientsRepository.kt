package cz.vvoleman.phr.common.domain.repository.patient

import cz.vvoleman.phr.common.domain.model.PatientDomainModel

interface GetAllPatientsRepository {

    suspend fun getAll(): List<PatientDomainModel>
}
