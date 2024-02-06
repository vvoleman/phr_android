package cz.vvoleman.phr.featureEvent.presentation.model.list

import cz.vvoleman.phr.featureEvent.presentation.model.core.EventPresentationModel

sealed class ListEventNotification {

    data class ExportEvents(val events: List<EventPresentationModel>) : ListEventNotification()

}
