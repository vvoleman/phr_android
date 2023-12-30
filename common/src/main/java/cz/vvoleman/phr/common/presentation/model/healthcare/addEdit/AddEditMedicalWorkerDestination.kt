package cz.vvoleman.phr.common.presentation.model.healthcare.addEdit

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class AddEditMedicalWorkerDestination: PresentationDestination {
    data class Saved(val id: String) : AddEditMedicalWorkerDestination()
}
