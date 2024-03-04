package cz.vvoleman.phr.featureMeasurement.ui.model.core

import cz.vvoleman.phr.common.ui.model.patient.PatientUiModel
import cz.vvoleman.phr.common.ui.model.problemCategory.ProblemCategoryUiModel
import java.time.DayOfWeek
import java.time.LocalTime

data class MeasurementGroupUiModel(
    val id: String,
    val name: String,
    val patient: PatientUiModel,
    val problemCategory: ProblemCategoryUiModel?,
    val scheduleItems: List<MeasurementGroupScheduleItemUiModel>,
    val fields: List<MeasurementGroupFieldUi>,
    val entries: List<MeasurementGroupEntryUiModel>,
) {
    fun getTimes(): List<LocalTime> {
        return scheduleItems.map { it.time }.toSet().sorted()
    }

    fun getDays(): List<DayOfWeek> {
        return scheduleItems.map { it.dayOfWeek }.toSet().sorted()
    }
}
