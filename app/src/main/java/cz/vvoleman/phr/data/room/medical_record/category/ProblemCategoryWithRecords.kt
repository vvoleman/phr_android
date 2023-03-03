package cz.vvoleman.phr.data.room.medical_record.category

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.data.room.medical_record.MedicalRecordEntity

data class ProblemCategoryWithRecords(
    @Embedded val problemCategory: ProblemCategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "problem_category_id"
    )
    val records: List<MedicalRecordEntity>
) {

    companion object {
        fun from(problemCategoryWithRecords: cz.vvoleman.phr.data.core.medical_record.ProblemCategoryWithRecords): ProblemCategoryWithRecords {
            return ProblemCategoryWithRecords(
                problemCategory = ProblemCategoryEntity.from(problemCategoryWithRecords.problemCategory),
                records = problemCategoryWithRecords.records.map { MedicalRecordEntity.from(it) }
            )
        }
    }

    fun toCore(): cz.vvoleman.phr.data.core.medical_record.ProblemCategoryWithRecords {
        return cz.vvoleman.phr.data.core.medical_record.ProblemCategoryWithRecords(
            problemCategory = problemCategory.toProblemCategory(),
            records = records.map { it. }
        )
    }

}
