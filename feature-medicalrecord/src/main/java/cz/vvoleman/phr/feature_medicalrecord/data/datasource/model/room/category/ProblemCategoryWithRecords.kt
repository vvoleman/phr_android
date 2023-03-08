package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDataSourceModel

data class ProblemCategoryWithRecords(
    @Embedded(prefix="parent_") val problemCategory: ProblemCategoryDataSourceModel,
    @Relation(
        parentColumn = "parent_id",
        entityColumn = "problem_category_id"
    )
    val records: List<MedicalWorkerDataSourceModel>
)
