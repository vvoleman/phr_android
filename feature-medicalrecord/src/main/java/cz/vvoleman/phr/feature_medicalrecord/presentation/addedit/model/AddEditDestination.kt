package cz.vvoleman.phr.feature_medicalrecord.presentation.addedit.model

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class AddEditDestination : PresentationDestination {
    data class RecordSaved(val id: String) : AddEditDestination()
    data class AddRecordFile(val previousViewState: AddEditViewState) : AddEditDestination()
}