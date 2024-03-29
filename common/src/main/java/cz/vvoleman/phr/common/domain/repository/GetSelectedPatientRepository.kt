package cz.vvoleman.phr.common.domain.repository

import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import kotlinx.coroutines.flow.Flow

interface GetSelectedPatientRepository {

    fun getSelectedPatient(): Flow<PatientDomainModel>
}
