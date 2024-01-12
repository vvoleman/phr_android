package cz.vvoleman.featureMeasurement.domain.model

import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel

data class MeasurementGroupDomainModel(
    val id: String,
    val name: String,
    val patient: PatientDomainModel,
    val scheduleItems: List<MeasurementGroupScheduleItemDomainModel>,
    val fields: List<MeasurementGroupField>,
)
