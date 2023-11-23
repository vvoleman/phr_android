package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDataSourceModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel

class MedicalWorkerDataSourceModelToDomainMapper {

    fun toDomain(model: MedicalWorkerDataSourceModel): MedicalWorkerDomainModel {
        return MedicalWorkerDomainModel(
            id = model.id!!.toString(),
            name = model.name,
            patientId = model.patientId.toString(),
        )
    }

    fun toDataSource(model: MedicalWorkerDomainModel): MedicalWorkerDataSourceModel {
        return MedicalWorkerDataSourceModel(
            id = model.id.toInt(),
            name = model.name,
            patientId = model.patientId.toInt(),
        )
    }
}
