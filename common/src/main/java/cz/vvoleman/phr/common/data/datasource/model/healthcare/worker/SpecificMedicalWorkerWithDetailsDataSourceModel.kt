package cz.vvoleman.phr.common.data.datasource.model.healthcare.worker

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.common.data.datasource.model.healthcare.service.MedicalServiceDataSourceModel

data class SpecificMedicalWorkerWithDetailsDataSourceModel(
    @Embedded val specificMedicalWorker: SpecificMedicalWorkerDataSourceModel,
    @Relation(
        parentColumn = "medical_worker_id",
        entityColumn = "id"
    )
    val medicalWorker: MedicalWorkerDataSourceModel,
    @Relation(
        parentColumn = "medical_service_id",
        entityColumn = "id"
    )
    val medicalService: MedicalServiceDataSourceModel,
)
