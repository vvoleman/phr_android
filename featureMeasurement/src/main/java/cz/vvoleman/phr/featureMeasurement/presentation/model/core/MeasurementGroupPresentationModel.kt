package cz.vvoleman.phr.featureMeasurement.presentation.model.core

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.presentation.model.problemCategory.ProblemCategoryPresentationModel

data class MeasurementGroupPresentationModel(
    val id: String,
    val name: String,
    val patient: PatientPresentationModel,
    val problemCategory: ProblemCategoryPresentationModel?,
    val scheduleItems: List<MeasurementGroupScheduleItemPresentationModel>,
    val fields: List<MeasurementGroupFieldPresentation>,
    val entries: List<MeasurementGroupEntryPresentationModel>,
)
