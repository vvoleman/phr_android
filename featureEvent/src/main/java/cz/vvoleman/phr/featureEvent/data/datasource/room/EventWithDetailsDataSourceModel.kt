package cz.vvoleman.phr.featureEvent.data.datasource.room

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.healthcare.worker.SpecificMedicalWorkerDataSourceModel

data class EventWithDetailsDataSourceModel(
    @Embedded val event: EventDataSourceModel,
    @Relation(
        parentColumn = "patient_id",
        entityColumn = "id"
    )
    val patient: PatientDataSourceModel,
    @Relation(
        parentColumn = "specific_medical_worker_id",
        entityColumn = "id"
    )
    val specificMedicalWorker: SpecificMedicalWorkerDataSourceModel?,
)
