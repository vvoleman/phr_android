package cz.vvoleman.phr.featureMedicalRecord.presentation.model.list

import cz.vvoleman.phr.featureMedicalRecord.presentation.model.export.ExportMedicalRecordParamsPresentationModel

sealed class ListMedicalRecordNotification {
    object NotImplemented : ListMedicalRecordNotification()
    object ExportFailed : ListMedicalRecordNotification()
    data class RecordDeleted(val id: String) : ListMedicalRecordNotification()
    data class Export(val params: List<ExportMedicalRecordParamsPresentationModel>) : ListMedicalRecordNotification()
}
