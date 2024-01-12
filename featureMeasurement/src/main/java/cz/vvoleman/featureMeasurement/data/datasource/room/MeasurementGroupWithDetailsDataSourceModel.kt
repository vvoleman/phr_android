package cz.vvoleman.featureMeasurement.data.datasource.room

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.featureMeasurement.data.datasource.room.fieldType.NumericFieldTypeDataSourceModel
import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel

data class MeasurementGroupWithDetailsDataSourceModel(
    @Embedded val measurementGroup: MeasurementGroupDataSourceModel,
    @Relation(
        parentColumn = "patient_id",
        entityColumn = "id"
    )
    val patient: PatientDataSourceModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "measurement_group_id"
    )
    val scheduleItems: List<MeasurementGroupScheduleItemDataSourceModel>,
    @Relation(
        parentColumn = "id",
        entityColumn = "measurement_group_id"
    )
    val numericFields: List<NumericFieldTypeDataSourceModel>
)
