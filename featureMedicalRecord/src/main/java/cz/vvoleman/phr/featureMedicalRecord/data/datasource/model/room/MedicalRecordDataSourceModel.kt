package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "medical_record")
data class MedicalRecordDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "patient_id") val patientId: Int,
    @ColumnInfo(name = "problem_category_id") val problemCategoryId: Int? = null,
    @ColumnInfo(name = "diagnose_id") val diagnoseId: String? = null,
    @ColumnInfo(name = "specific_medical_worker_id") val specificMedicalWorkerId: Int? = null,
    @ColumnInfo(name = "created_at") val createdAt: LocalDate = LocalDate.now(),
    @ColumnInfo(name = "visit_date") val visitDate: LocalDate,
    val comment: String = ""
)
