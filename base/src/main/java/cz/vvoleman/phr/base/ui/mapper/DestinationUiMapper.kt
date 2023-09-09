package cz.vvoleman.phr.base.ui.mapper

import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.navigation.NavManager

abstract class DestinationUiMapper(
    protected val navManager: NavManager
) {
    abstract fun navigate(destination: PresentationDestination)
}
