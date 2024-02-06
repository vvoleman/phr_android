package cz.vvoleman.phr.featureEvent.presentation.model.addEdit

import cz.vvoleman.phr.common.presentation.model.healthcare.core.SpecificMedicalWorkerPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import cz.vvoleman.phr.featureEvent.presentation.model.core.EventPresentationModel
import java.time.LocalDate
import java.time.LocalTime

data class AddEditEventViewState(
    val patient: PatientPresentationModel,
    val event: EventPresentationModel? = null,
    val workers: List<SpecificMedicalWorkerPresentationModel> = emptyList(),
    val name: String = "",
    val description: String = "",
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val reminders: List<ReminderPresentationModel> = emptyList(),
    val selectedWorker: SpecificMedicalWorkerPresentationModel? = null,
    val areRemindersEnabled: Boolean = false
) {

    val missingFields: List<RequiredField>
        get() {
            val missingFields = mutableListOf<RequiredField>()
            if (name.isEmpty()) {
                missingFields.add(RequiredField.NAME)
            }
            if (date == null) {
                missingFields.add(RequiredField.DATE)
            }
            if (time == null) {
                missingFields.add(RequiredField.TIME)
            }
            return missingFields
        }

    enum class RequiredField {
        NAME,
        DATE,
        TIME,
    }

}
