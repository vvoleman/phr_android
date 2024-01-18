package cz.vvoleman.phr.featureMedicalRecord.presentation.model.list

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class ListMedicalRecordDestination : PresentationDestination {

    object NewMedicalRecord : ListMedicalRecordDestination()
    data class EditMedicalRecord(val id: String) : ListMedicalRecordDestination()
    data class DetailMedicalRecord(val id: String) : ListMedicalRecordDestination()
}
