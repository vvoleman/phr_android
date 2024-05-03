package cz.vvoleman.phr.featureMedicine.presentation.addEdit.model

sealed class AddEditMedicineNotification {

    object CannotSave : AddEditMedicineNotification()

    object MedicineScheduleNotFound : AddEditMedicineNotification()

    object CannotSchedule : AddEditMedicineNotification()
}
