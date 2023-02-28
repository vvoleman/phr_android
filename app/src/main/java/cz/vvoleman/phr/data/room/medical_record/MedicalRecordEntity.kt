package cz.vvoleman.phr.data.room.medical_record

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.core.medical_record.MedicalRecord
import java.time.LocalDate

@Entity(tableName = "medical_record")
data class MedicalRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val patient_id: Int,
    val problem_category_id: Int? = null,
    val diagnose_id: String? = null,
    val medical_worker_id: Int? = null,
    val created_at: LocalDate = LocalDate.now(),
    val comment: String = ""
) {

    companion object {
        fun from(medicalRecord: MedicalRecord): MedicalRecordEntity {
            return MedicalRecordEntity(
                id = medicalRecord.id,
                patient_id = medicalRecord.patient.id!!,
                problem_category_id = medicalRecord.problemCategory?.id,
                diagnose_id = medicalRecord.diagnose?.id,
                medical_worker_id = medicalRecord.medicalWorker?.id,
                created_at = medicalRecord.createdAt,
                comment = medicalRecord.comment
            )
        }
    }

}