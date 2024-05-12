package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDao
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerWithDetailsDataSourceModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.SpecificMedicalWorkerDomainModel
import kotlinx.coroutines.flow.first

class SpecificMedicalWorkerDataSourceToDomainMapper(
    private val workerDao: MedicalWorkerDao,
    private val serviceDao: MedicalServiceDao,
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

    suspend fun toDetails(
        model: SpecificMedicalWorkerDataSourceModel
    ): SpecificMedicalWorkerWithDetailsDataSourceModel {
        val worker = workerDao.getById(model.medicalWorkerId).first()
        val service = serviceDao.getById(model.medicalServiceId).first()

        return SpecificMedicalWorkerWithDetailsDataSourceModel(
            specificMedicalWorker = model,
            medicalWorker = worker,
            medicalService = service,
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
