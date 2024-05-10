package cz.vvoleman.phr.featureEvent.presentation.model.list

import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureEvent.presentation.model.core.EventPresentationModel
import java.time.LocalDate
import java.time.LocalDateTime

data class ListEventViewState(
    val totalCount: Int,
    val patient: PatientPresentationModel,
    val events: Map<LocalDate, List<EventPresentationModel>>,
    val dateTimeLimit: LocalDateTime = LocalDateTime.now()
) {
    val currentCount: Int = events.values.sumOf { it.size }
}
