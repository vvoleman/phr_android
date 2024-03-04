package cz.vvoleman.phr.featureMeasurement.domain.model.core

import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel

data class MeasurementGroupDomainModel(
    val id: String,
    val name: String,
    val patient: PatientDomainModel,
    val problemCategory: ProblemCategoryDomainModel?,
    val scheduleItems: List<MeasurementGroupScheduleItemDomainModel>,
    val fields: List<MeasurementGroupFieldDomain>,
    val entries: List<MeasurementGroupEntryDomainModel>,
)
