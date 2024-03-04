package cz.vvoleman.phr.featureMeasurement.data.datasource.room

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.problemCategory.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.featureMeasurement.data.datasource.room.field.NumericFieldDataSourceModel

data class MeasurementGroupWithDetailsDataSourceModel(
    @Embedded val measurementGroup: MeasurementGroupDataSourceModel,
    @Relation(
        parentColumn = "patient_id",
        entityColumn = "id"
    )
    val patient: PatientDataSourceModel,

    @Relation(
        parentColumn = "problem_category_id",
        entityColumn = "id"
    )
    val problemCategory: ProblemCategoryDataSourceModel?,

    @Relation(
        parentColumn = "id",
        entityColumn = "measurement_group_id"
    )
    val scheduleItems: List<MeasurementGroupScheduleItemDataSourceModel>,

    @Relation(
        parentColumn = "id",
        entityColumn = "measurement_group_id"
    )
    val entries: List<MeasurementGroupEntryDataSourceModel>,

    @Relation(
        parentColumn = "id",
        entityColumn = "measurement_group_id"
    )
    val numericFields: List<NumericFieldDataSourceModel>
)
