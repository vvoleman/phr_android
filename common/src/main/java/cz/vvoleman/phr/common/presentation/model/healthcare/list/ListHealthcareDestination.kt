package cz.vvoleman.phr.common.presentation.model.healthcare.list

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class ListHealthcareDestination : PresentationDestination {
    object AddMedicalWorker : ListHealthcareDestination()

    data class EditMedicalWorker(val id: String) : ListHealthcareDestination()

    data class DetailMedicalWorker(val id: String) : ListHealthcareDestination()
}
