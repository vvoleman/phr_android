package cz.vvoleman.phr.featureMeasurement.domain.facade

import cz.vvoleman.phr.featureMeasurement.domain.model.core.MeasurementGroupDomainModel
import java.time.LocalDateTime

class NextMeasurementGroupScheduleFacade(
    private val translateDateTimeFacade: MeasurementTranslateDateTimeFacade
) {

    fun getNextSchedule(
        list: List<MeasurementGroupDomainModel>,
        currentDateTime: LocalDateTime
    ) : List<MeasurementGroupDomainModel> {
        val translated = list.groupBy { model ->
            model.scheduleItems.minOf { translateDateTimeFacade.getLocalTime(it, currentDateTime) }
        }.toSortedMap()

        return translated.values.firstOrNull() ?: emptyList()
    }

}
