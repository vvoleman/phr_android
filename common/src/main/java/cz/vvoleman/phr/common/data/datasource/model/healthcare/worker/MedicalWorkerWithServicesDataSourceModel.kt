package cz.vvoleman.phr.common.data.datasource.model.healthcare.worker

import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceWithInfoDataSourceModel

data class MedicalWorkerWithServicesDataSourceModel(
    val medicalWorker: MedicalWorkerDataSourceModel,
    val medicalServices: List<MedicalServiceWithInfoDataSourceModel>
)
