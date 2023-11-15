package cz.vvoleman.phr.base.ui.exception

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

class UnknownDestinationException(
    destination: PresentationDestination
) : UiException(
    "Cannot navigate to ${destination::class.simpleName}"
)
