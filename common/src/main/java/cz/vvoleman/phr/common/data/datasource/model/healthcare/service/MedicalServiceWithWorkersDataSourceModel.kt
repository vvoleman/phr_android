package cz.vvoleman.phr.common.data.datasource.model.healthcare.service

import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.MedicalWorkerWithInfoDataSourceModel

data class MedicalServiceWithWorkersDataSourceModel(
    val medicalService: MedicalServiceDataSourceModel,
    val medicalWorkers: List<MedicalWorkerWithInfoDataSourceModel>
)
