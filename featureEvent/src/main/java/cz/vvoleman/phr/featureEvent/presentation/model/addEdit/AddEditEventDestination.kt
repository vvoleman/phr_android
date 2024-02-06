package cz.vvoleman.phr.featureEvent.presentation.model.addEdit

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class AddEditEventDestination : PresentationDestination {
    data class Saved(val id: String) : AddEditEventDestination()
}
