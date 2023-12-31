package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerWithDetailsDataSourceModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel

class SpecificMedicalWorkerDataSourceToDomainMapper(
    private val workerMapper: MedicalWorkerDataSourceModelToDomainMapper,
    private val serviceInfoMapper: MedicalServiceDataSourceModelToDomainMapper,
) {

    fun toDomain(model: SpecificMedicalWorkerWithDetailsDataSourceModel): SpecificMedicalWorkerDomainModel {
        return SpecificMedicalWorkerDomainModel(
            id = model.specificMedicalWorker.id.toString(),
            medicalWorker = workerMapper.toDomain(model.medicalWorker),
            medicalService = serviceInfoMapper.toDomain(model.medicalService),
            email = model.specificMedicalWorker.email,
            telephone = model.specificMedicalWorker.telephone,
        )
    }

    fun toDataSource(model: SpecificMedicalWorkerDomainModel): SpecificMedicalWorkerWithDetailsDataSourceModel {
        return SpecificMedicalWorkerWithDetailsDataSourceModel(
            medicalWorker = workerMapper.toDataSource(model.medicalWorker),
            medicalService = serviceInfoMapper.toDataSource(model.medicalService),
            specificMedicalWorker = SpecificMedicalWorkerDataSourceModel(
                id = model.id?.toIntOrNull(),
                medicalWorkerId = model.medicalWorker.id!!.toInt(),
                medicalServiceId = model.medicalService.id,
                email = model.email,
                telephone = model.telephone,
            )
        )
    }
}
