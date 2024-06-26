package cz.vvoleman.phr.featureMedicine.presentation.list.model

import cz.vvoleman.phr.featureMedicine.presentation.export.model.ExportParamsPresentationModel
import java.time.LocalDateTime

sealed class ListMedicineNotification {

    object DataLoaded : ListMedicineNotification()
    object UnableToLoad : ListMedicineNotification()
    object Deleted : ListMedicineNotification()
    object ScheduleNotDeleted : ListMedicineNotification()
    object AlarmNotDeleted : ListMedicineNotification()
    data class OpenSchedule(val dateTime: LocalDateTime, val items: List<ScheduleItemWithDetailsPresentationModel>) :
        ListMedicineNotification()

    object UnableToToggleAlarm : ListMedicineNotification()
    data class Export(val params: ExportParamsPresentationModel) : ListMedicineNotification()
}
