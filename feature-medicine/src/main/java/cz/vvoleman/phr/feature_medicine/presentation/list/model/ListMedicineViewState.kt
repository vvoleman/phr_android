package cz.vvoleman.phr.feature_medicine.presentation.list.model

import cz.vvoleman.phr.feature_medicine.presentation.model.list.MedicinePresentationModel

data class ListMedicineViewState(
    val medicines: List<MedicinePresentationModel> = emptyList()
)