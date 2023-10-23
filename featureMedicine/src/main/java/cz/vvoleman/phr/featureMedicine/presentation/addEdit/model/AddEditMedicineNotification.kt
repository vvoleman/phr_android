package cz.vvoleman.phr.featureMedicine.presentation.addEdit.model

sealed class AddEditMedicineNotification {

    object DataLoaded : AddEditMedicineNotification()

    object CannotSave : AddEditMedicineNotification()

    object MedicineScheduleNotFound : AddEditMedicineNotification()

    object CannotScheduleMedicine : AddEditMedicineNotification()
}
