package cz.vvoleman.phr.feature_medicalrecord.presentation.list.model

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class ListMedicalRecordsDestination : PresentationDestination {

    object NewMedicalRecord : ListMedicalRecordsDestination()
    data class EditMedicalRecord(val id: String) : ListMedicalRecordsDestination()

}