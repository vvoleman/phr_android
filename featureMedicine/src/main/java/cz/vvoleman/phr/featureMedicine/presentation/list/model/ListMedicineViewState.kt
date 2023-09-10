package cz.vvoleman.phr.featureMedicine.presentation.list.model

import cz.vvoleman.phr.featureMedicine.presentation.model.list.MedicinePresentationModel

data class ListMedicineViewState(
    val medicines: List<MedicinePresentationModel> = emptyList()
)
