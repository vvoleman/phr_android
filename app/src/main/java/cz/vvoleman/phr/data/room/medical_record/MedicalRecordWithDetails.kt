package cz.vvoleman.phr.data.room.medical_record

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.data.core.medical_record.MedicalRecord
import cz.vvoleman.phr.data.room.diagnose.DiagnoseEntity
import cz.vvoleman.phr.data.room.medical_record.asset.MedicalRecordAssetEntity
import cz.vvoleman.phr.data.room.medical_record.worker.MedicalWorkerEntity
import cz.vvoleman.phr.data.room.patient.PatientEntity

data class MedicalRecordWithDetails(
    @Embedded val medicalRecord: MedicalRecordEntity,
    @Relation(
        parentColumn = "patient_id",
        entityColumn = "id"
    )
    val patient: PatientEntity,

    @Relation(
        parentColumn = "diagnose_id",
        entityColumn = "id"
    )
    val diagnose: DiagnoseEntity?,

    @Relation(
        parentColumn = "medical_worker_id",
        entityColumn = "id"
    )
    val medicalWorker: MedicalWorkerEntity?,

    @Relation(
        parentColumn = "id",
        entityColumn = "medical_record_id"
    )
    val assets: List<MedicalRecordAssetEntity>,
) {

    fun toMedicalRecord() : MedicalRecord {
        return MedicalRecord(
            id = medicalRecord.id,
            patient = patient.toPatient(),
            diagnose = diagnose?.toDiagnose(),
            medicalWorker = medicalWorker?.toMedicalWorker(),
            createdAt = medicalRecord.created_at,
            assets = assets.map { it.toMedicalRecordAsset() },
        )
    }

}
