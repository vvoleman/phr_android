package cz.vvoleman.phr.common.presentation.model.healthcare.addEdit

import androidx.paging.PagingData
import cz.vvoleman.phr.common.presentation.model.healthcare.core.MedicalFacilityPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import kotlinx.coroutines.flow.Flow

data class AddEditMedicalWorkerViewState(
    val workerId: String? = null,
    val patient: PatientPresentationModel? = null,
    val name: String? = null,
    val details: List<AddEditMedicalServiceItemPresentationModel> = emptyList(),
    val query: String = "",
    val facilityStream: Flow<PagingData<MedicalFacilityPresentationModel>>? = null,
) {
    val missingFields: List<RequiredField>
        get() {
            val missingFields = mutableListOf<RequiredField>()
            if (name.isNullOrBlank()) {
                missingFields.add(RequiredField.NAME)
            }
            if (details.isEmpty()) {
                missingFields.add(RequiredField.CONTACT)
            }
            if (details.any { it.serviceId.isNullOrBlank() }) {
                missingFields.add(RequiredField.SERVICE)
            }
            if (details.any { it.facility == null }) {
                missingFields.add(RequiredField.FACILITY)
            }
            return missingFields
        }

    enum class RequiredField {
        NAME,
        CONTACT,
        SERVICE,
        FACILITY
    }
}
