package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.PatientDomainModel
import kotlinx.coroutines.flow.Flow

interface GetSelectedPatientRepository {

    fun getSelectedPatient(): Flow<PatientDomainModel>

}