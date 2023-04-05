package cz.vvoleman.phr.feature_medicine.presentation.list.model

import cz.vvoleman.phr.feature_medicine.domain.model.medicine.MedicineDomainModel

data class ListMedicineViewState(
    val medicines: List<MedicineDomainModel> = emptyList()
)