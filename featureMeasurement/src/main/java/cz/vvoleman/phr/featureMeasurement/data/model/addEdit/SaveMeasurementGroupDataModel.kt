package cz.vvoleman.phr.featureMeasurement.data.model.addEdit

import cz.vvoleman.phr.featureMeasurement.data.model.core.MeasurementGroupFieldData

data class SaveMeasurementGroupDataModel(
    val id: String?,
    val name: String,
    val patientId: String,
    val fields: List<MeasurementGroupFieldData>,
    val scheduleItems: List<SaveMeasurementGroupScheduleItemDataModel>,
)
