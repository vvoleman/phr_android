package cz.vvoleman.phr.featureMeasurement.domain.model.addEdit

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupFieldDomain

data class SaveMeasurementGroupDomainModel(
    val id: String?,
    val name: String,
    val patientId: String,
    val fields: List<MeasurementGroupFieldDomain>,
    val scheduleItems: List<SaveMeasurementGroupScheduleItemDomainModel>,
)
