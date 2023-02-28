package cz.vvoleman.phr.data.room.medical_record.category

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.data.room.medical_record.MedicalRecordEntity

data class ProblemCategoryWithRecords(
    @Embedded(prefix="parent_") val problemCategory: ProblemCategoryEntity,
    @Relation(
        parentColumn = "parent_id",
        entityColumn = "problem_category_id"
    )
    val records: List<MedicalRecordEntity>
)
