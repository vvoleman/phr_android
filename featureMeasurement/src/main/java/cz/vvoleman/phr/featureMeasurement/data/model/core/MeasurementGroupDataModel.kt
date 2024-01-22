package cz.vvoleman.phr.featureMeasurement.data.model.core

import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel

data class MeasurementGroupDataModel(
    val id: String,
    val name: String,
    val patient: PatientDomainModel,
    val scheduleItems: List<MeasurementGroupScheduleItemDataModel>,
    val entries: List<MeasurementGroupEntryDataModel>,
    val fields: List<MeasurementGroupFieldData>,
)