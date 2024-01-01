package cz.vvoleman.phr.common.data.datasource.model.problemCategory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "problem_category")
data class ProblemCategoryDataSourceModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
    val color: String,
    @ColumnInfo(name = "patient_id") val patientId: Int
)
