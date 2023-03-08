package cz.vvoleman.phr.base.ui.exception

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

class UnknownDestinationException(
    destination: PresentationDestination
) : IllegalArgumentException(
    "Cannot navigate to ${destination::class.simpleName}"
) {
}