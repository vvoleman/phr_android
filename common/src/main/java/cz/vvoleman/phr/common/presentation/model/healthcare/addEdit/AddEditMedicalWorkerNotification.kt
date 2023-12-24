package cz.vvoleman.phr.common.presentation.model.healthcare.addEdit

import androidx.paging.PagingData
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.presentation.viewmodel.healthcare.AddEditMedicalWorkerViewModel
import kotlinx.coroutines.flow.Flow

sealed class AddEditMedicalWorkerNotification {
    data class FacilityStreamChanged(val stream: Flow<PagingData<MedicalFacilityPresentationModel>>) :
        AddEditMedicalWorkerNotification()

    data class MissingFields(val fields: List<AddEditMedicalWorkerViewModel.RequiredField>) :
        AddEditMedicalWorkerNotification()
}
