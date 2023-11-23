package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerWithServicesDataSourceModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithServicesDomainModel

class MedicalWorkerWithServicesDataSourceModelToDomainMapper(
    private val workerMapper: MedicalWorkerDataSourceModelToDomainMapper,
    private val serviceInfoMapper: MedicalServiceWithInfoDataSourceModelToDomainMapper,
) {

    fun toDomain(model: MedicalWorkerWithServicesDataSourceModel): MedicalWorkerWithServicesDomainModel {
        return MedicalWorkerWithServicesDomainModel(
            medicalWorker = workerMapper.toDomain(model.medicalWorker),
            services = model.medicalServices.map { serviceInfoMapper.toDomain(it) }
        )
    }

    fun toDataSource(model: MedicalWorkerWithServicesDomainModel): MedicalWorkerWithServicesDataSourceModel {
        return MedicalWorkerWithServicesDataSourceModel(
            medicalWorker = workerMapper.toDataSource(model.medicalWorker),
            medicalServices = model.services.map { serviceInfoMapper.toDataSource(it) }
        )
    }
}
