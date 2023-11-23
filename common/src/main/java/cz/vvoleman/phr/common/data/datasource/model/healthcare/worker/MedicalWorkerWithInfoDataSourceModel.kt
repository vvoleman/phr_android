package cz.vvoleman.phr.common.data.datasource.model.healthcare.worker

import androidx.room.Embedded

data class MedicalWorkerWithInfoDataSourceModel(
    @Embedded val medicalWorker: MedicalWorkerDataSourceModel,
    val email: String? = null,
    val telephone: String? = null,
)
