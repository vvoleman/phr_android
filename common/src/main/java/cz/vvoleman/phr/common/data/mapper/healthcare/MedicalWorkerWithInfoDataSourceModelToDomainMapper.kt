package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerWithInfoDataSourceModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithInfoDomainModel

class MedicalWorkerWithInfoDataSourceModelToDomainMapper(
    private val medicalWorkerDataSourceModelToDomainMapper: MedicalWorkerDataSourceModelToDomainMapper
) {

    fun toDomain(model: MedicalWorkerWithInfoDataSourceModel): MedicalWorkerWithInfoDomainModel {
        return MedicalWorkerWithInfoDomainModel(
            medicalWorker = medicalWorkerDataSourceModelToDomainMapper.toDomain(model.medicalWorker),
            email = model.email,
            telephone = model.telephone,
        )
    }

    fun toDataSource(model: MedicalWorkerWithInfoDomainModel): MedicalWorkerWithInfoDataSourceModel {
        return MedicalWorkerWithInfoDataSourceModel(
            medicalWorker = medicalWorkerDataSourceModelToDomainMapper.toDataSource(model.medicalWorker),
            email = model.email,
            telephone = model.telephone,
        )
    }
}
