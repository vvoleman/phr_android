package cz.vvoleman.phr.featureMedicine.presentation.list.model

sealed class ListMedicineNotification {

    object DataLoaded : ListMedicineNotification()
    object UnableToLoad : ListMedicineNotification()
    object Deleted : ListMedicineNotification()
    object ScheduleNotDeleted : ListMedicineNotification()
    object AlarmNotDeleted : ListMedicineNotification()
}
