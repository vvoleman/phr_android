package cz.vvoleman.phr.data.room.medical_record.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.vvoleman.phr.data.core.Color
import cz.vvoleman.phr.data.core.medical_record.ProblemCategory
import java.time.LocalDate

@Entity(tableName = "problem_category")
data class ProblemCategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val createdAt: LocalDate,
    val color: Color,
    val patient_id: Int
) {

    companion object {
        fun from(problemCategory: ProblemCategory) : ProblemCategoryEntity{
            return ProblemCategoryEntity(
                id = problemCategory.id,
                name = problemCategory.name,
                createdAt = problemCategory.createdAt,
                color = problemCategory.color,
                patient_id = problemCategory.patientId
            )
        }
    }

    fun toProblemCategory() : ProblemCategory {
        return ProblemCategory(
            id = id,
            name = name,
            createdAt = createdAt,
            color = color,
            patientId = patient_id
        )
    }

}
