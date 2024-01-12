package cz.vvoleman.featureMeasurement.data.model

import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupField
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel

data class MeasurementGroupDataModel(
    val id: String,
    val name: String,
    val patient: PatientDomainModel,
    val scheduleItems: List<MeasurementGroupScheduleItemDataModel>,
    val fields: List<MeasurementGroupField>,
)
