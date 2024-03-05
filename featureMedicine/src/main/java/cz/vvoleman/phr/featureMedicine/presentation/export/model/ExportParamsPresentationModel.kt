package cz.vvoleman.phr.featureMedicine.presentation.export.model

import cz.vvoleman.phr.featureMedicine.presentation.list.model.MedicineSchedulePresentationModel

data class ExportParamsPresentationModel(
    val medicineSchedules: List<MedicineSchedulePresentationModel>,
)
