package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceWithInfoDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerWithServicesDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDao
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithServicesDomainModel
import kotlinx.coroutines.flow.first

class MedicalWorkerWithServicesDataSourceModelToDomainMapper(
    private val specificWorkerDao: SpecificMedicalWorkerDao,
    private val workerMapper: MedicalWorkerDataSourceModelToDomainMapper,
    private val serviceInfoMapper: MedicalServiceWithInfoDataSourceModelToDomainMapper,
) {

    fun toDomain(model: MedicalWorkerWithServicesDataSourceModel): MedicalWorkerWithServicesDomainModel {
        return MedicalWorkerWithServicesDomainModel(
            medicalWorker = workerMapper.toDomain(model.medicalWorker),
            services = model.medicalServices.map { serviceInfoMapper.toDomain(it) }
        )
    }

    suspend fun toDomain(models: List<MedicalWorkerDataSourceModel>): List<MedicalWorkerWithServicesDomainModel> {
        val specificWorkers = specificWorkerDao.getByMedicalService(models.map { it.id!! }).first()

        val specificMap = specificWorkers.groupBy { it.medicalWorker }

        return specificMap.map { (worker, specificWorkers) ->
            MedicalWorkerWithServicesDomainModel(
                medicalWorker = workerMapper.toDomain(worker),
                services = specificWorkers.map {
                    serviceInfoMapper.toDomain(
                        MedicalServiceWithInfoDataSourceModel(
                            medicalService = it.medicalService,
                            email = it.specificMedicalWorker.email,
                            telephone = it.specificMedicalWorker.telephone
                        )
                    )
                }
            )
        }
    }

    fun toDataSource(model: MedicalWorkerWithServicesDomainModel): MedicalWorkerWithServicesDataSourceModel {
        return MedicalWorkerWithServicesDataSourceModel(
            medicalWorker = workerMapper.toDataSource(model.medicalWorker),
            medicalServices = model.services.map { serviceInfoMapper.toDataSource(it) }
        )
    }
}
