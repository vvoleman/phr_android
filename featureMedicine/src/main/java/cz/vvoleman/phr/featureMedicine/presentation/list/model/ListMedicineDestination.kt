package cz.vvoleman.phr.featureMedicine.presentation.list.model

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class ListMedicineDestination : PresentationDestination {

    data class EditSchedule(val id: String) : PresentationDestination
    object CreateSchedule : PresentationDestination
}
