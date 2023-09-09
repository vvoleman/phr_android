package cz.vvoleman.phr.feature_medicalrecord.data.mapper

import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.domain.model.MedicalWorkerDomainModel

class MedicalWorkerDataSourceToDomainMapper(
    private val addressMapper: AddressDataSourceToDomainMapper
) {

    fun toDomain(medicalWorker: MedicalWorkerDataSourceModel): MedicalWorkerDomainModel {
        return MedicalWorkerDomainModel(
            id = medicalWorker.id!!.toString(),
            name = medicalWorker.name,
            patientId = medicalWorker.patientId.toString(),
            email = medicalWorker.email,
            phone = medicalWorker.phone,
            address = addressMapper.toDomain(medicalWorker.address)
        )
    }
}
