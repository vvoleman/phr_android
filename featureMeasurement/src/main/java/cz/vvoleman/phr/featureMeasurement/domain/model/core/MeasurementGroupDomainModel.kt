package cz.vvoleman.phr.featureMeasurement.domain.model.core

import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel

data class MeasurementGroupDomainModel(
    val id: String,
    val name: String,
    val patient: PatientDomainModel,
    val scheduleItems: List<MeasurementGroupScheduleItemDomainModel>,
    val fields: List<MeasurementGroupFieldDomain>,
    val entries: List<MeasurementGroupEntryDomainModel>,
)
