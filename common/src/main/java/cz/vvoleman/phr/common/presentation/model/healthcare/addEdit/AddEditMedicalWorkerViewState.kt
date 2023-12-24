package cz.vvoleman.phr.common.presentation.model.healthcare.addEdit

import androidx.paging.PagingData
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.common.ui.model.healthcare.addEdit.AddEditMedicalServiceItemUiModel
import kotlinx.coroutines.flow.Flow

data class AddEditMedicalWorkerViewState(
    val workerId: String? = null,
    val patient: PatientPresentationModel? = null,
    val name: String = "",
    val details: List<AddEditMedicalServiceItemPresentationModel> = emptyList(),
    val query: String = "",
    val facilityStream: Flow<PagingData<MedicalFacilityPresentationModel>>? = null,
)
