package cz.vvoleman.phr.common.data.mapper.healthcare

import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceWithWorkersDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDao
import cz.vvoleman.phr.common.domain.model.healthcare.service.MedicalServiceWithWorkersDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithInfoDomainModel
import kotlinx.coroutines.flow.first

class MedicalServiceWithWorkersDataSourceModelToDomainMapper(
    private val specificWorkerDao: SpecificMedicalWorkerDao,
    private val serviceMapper: MedicalServiceDataSourceModelToDomainMapper,
    private val workerMapper: MedicalWorkerDataSourceModelToDomainMapper,
    private val workerWithInfoMapper: MedicalWorkerWithInfoDataSourceModelToDomainMapper,
) {

    suspend fun toDomain(model: MedicalServiceDataSourceModel): MedicalServiceWithWorkersDomainModel {
        val specificWorkers = specificWorkerDao.getByMedicalService(model.id!!).first()
        return MedicalServiceWithWorkersDomainModel(
            medicalService = serviceMapper.toDomain(model),
            workers = specificWorkers.map {
                MedicalWorkerWithInfoDomainModel(
                    medicalWorker = workerMapper.toDomain(it.medicalWorker),
                    email = it.specificMedicalWorker.email,
                    telephone = it.specificMedicalWorker.telephone,
                )
            }
        )
    }

    fun toDataSource(model: MedicalServiceWithWorkersDomainModel): MedicalServiceWithWorkersDataSourceModel {
        return MedicalServiceWithWorkersDataSourceModel(
            medicalService = serviceMapper.toDataSource(model.medicalService),
            medicalWorkers = model.workers.map { workerWithInfoMapper.toDataSource(it) }
        )
    }
}
