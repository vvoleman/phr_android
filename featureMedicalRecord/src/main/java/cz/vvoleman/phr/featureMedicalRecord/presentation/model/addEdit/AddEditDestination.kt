package cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit

import cz.vvoleman.phr.base.presentation.model.PresentationDestination

sealed class AddEditDestination : PresentationDestination {
    data class RecordSaved(val id: String) : AddEditDestination()
    data class AddRecordFile(val previousViewState: AddEditPresentationModel) : AddEditDestination()
}
