package cz.vvoleman.phr.featureMedicalRecord.data.datasource.model.room.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "problem_category")
data class ProblemCategoryDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val createdAt: LocalDate,
    val color: String,
    @ColumnInfo(name = "patient_id") val patientId: Int
)
