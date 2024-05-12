package cz.vvoleman.phr.featureEvent.ui.factory

import cz.vvoleman.phr.featureEvent.ui.model.core.EventUiModel
import cz.vvoleman.phr.featureEvent.ui.view.list.groupie.DayContainer
import cz.vvoleman.phr.featureEvent.ui.view.list.groupie.DayItem
import cz.vvoleman.phr.featureEvent.ui.view.list.groupie.MonthContainer
import java.time.LocalDate

class ListEventFactory {

    fun create(items: Map<LocalDate, List<EventUiModel>>, listener: DayItem.EventItemListener): List<MonthContainer> {
        val dayContainers = mutableMapOf<LocalDate, List<DayContainer>>()
        for ((date, events) in items) {
            val dayItems = events.map { event -> DayItem(event) }.onEach { it.setListener(listener) }
            val dayContainer = DayContainer(date, dayItems)
            val month = LocalDate.of(date.year, date.month, 1)
            val dayContainerList = dayContainers[month] ?: emptyList()
            dayContainers[month] = dayContainerList + dayContainer
        }

        return dayContainers.map { (month, dayContainerList) ->
            MonthContainer(month, dayContainerList)
        }
    }
}
