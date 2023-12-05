package cz.vvoleman.phr.common.presentation.model.healthcare.addEdit

import cz.vvoleman.phr.common.ui.model.healthcare.core.MedicalServiceUiModel

data class MedicalServiceDetailsPresentationModel(
    val medicalService: MedicalServiceUiModel,
    val telephone: String,
    val email: String,
)
