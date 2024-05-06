package cz.vvoleman.phr.featureMedicine.data.datasource.room.schedule

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.problemCategory.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.featureMedicine.data.datasource.room.medicine.MedicineDataSourceModel

data class ScheduleWithDetailsDataSourceModel(
    @Embedded val schedule: MedicineScheduleDataSourceModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "schedule_id"
    )
    val items: List<ScheduleItemDataSourceModel>,

    @Relation(
        parentColumn = "patient_id",
        entityColumn = "id"
    )
    val patient: PatientDataSourceModel,

    @Relation(
        parentColumn = "medicine_id",
        entityColumn = "id"

    )
    val medicine: MedicineDataSourceModel,

    @Relation(
        parentColumn = "problem_category_id",
        entityColumn = "id"
    )
    val problemCategory: ProblemCategoryDataSourceModel
)
