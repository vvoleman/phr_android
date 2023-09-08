package cz.vvoleman.phr.feature_medicine.presentation.list.model

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class ListMedicineDestination : PresentationDestination {

    data class EditSchedule(val id: String): PresentationDestination
    object CreateSchedule: PresentationDestination

}