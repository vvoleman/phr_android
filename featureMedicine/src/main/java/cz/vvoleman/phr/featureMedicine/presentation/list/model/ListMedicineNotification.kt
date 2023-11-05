package cz.vvoleman.phr.featureMedicine.presentation.list.model

sealed class ListMedicineNotification {

    object DataLoaded : ListMedicineNotification()

    object UnableToLoad : ListMedicineNotification()
}
