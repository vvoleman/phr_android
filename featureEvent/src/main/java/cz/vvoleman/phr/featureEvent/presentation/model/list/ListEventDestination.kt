package cz.vvoleman.phr.featureEvent.presentation.model.list

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class ListEventDestination : PresentationDestination {

    object AddEvent : ListEventDestination()
    data class EditEvent(val eventId: String) : ListEventDestination()
}
