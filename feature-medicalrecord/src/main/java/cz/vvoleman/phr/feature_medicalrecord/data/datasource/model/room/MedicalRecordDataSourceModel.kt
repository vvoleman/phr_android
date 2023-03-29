package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "medical_record")
data class MedicalRecordDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val patient_id: Int,
    val problem_category_id: Int? = null,
    val diagnose_id: String? = null,
    val medical_worker_id: Int? = null,
    val created_at: LocalDate = LocalDate.now(),
    val visit_date: LocalDate,
    val comment: String = ""
)