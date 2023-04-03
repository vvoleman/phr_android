package cz.vvoleman.phr.common.presentation.model.addedit

import cz.vvoleman.phr.base.presentation.model.FieldErrorState
import cz.vvoleman.phr.common.presentation.model.PatientPresentationModel

data class AddEditViewState(
    val patient: PatientPresentationModel? = null,
    val errorFields: Map<String,List<FieldErrorState>> = emptyMap()
) {
    fun hasErrors() = errorFields.isNotEmpty()
}