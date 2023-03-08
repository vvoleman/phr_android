package cz.vvoleman.phr.base.ui.mapper

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

interface DestinationUiMapper {
    fun navigate(destination: PresentationDestination)
}