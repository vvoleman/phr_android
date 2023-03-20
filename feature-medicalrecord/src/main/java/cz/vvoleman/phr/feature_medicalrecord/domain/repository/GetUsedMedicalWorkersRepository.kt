package cz.vvoleman.phr.feature_medicalrecord.domain.repository

import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalWorkerDomainModel

interface GetUsedMedicalWorkersRepository {

    fun getUsedMedicalWorkers(patientId: String): List<MedicalWorkerDomainModel>

}