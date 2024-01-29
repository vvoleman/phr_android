package cz.vvoleman.phr.featureMeasurement.presentation.model.addEditEntry

import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupEntryPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import java.time.LocalDateTime

data class AddEditEntryViewState(
    val existingEntry: MeasurementGroupEntryPresentationModel? = null,
    val measurementGroup: MeasurementGroupPresentationModel,
    val entryFields: Map<String, EntryFieldPresentationModel>,
    val dateTime: LocalDateTime? = null,
    val scheduleItemId: String? = null,
) {

    val errorFields: List<FieldError>
        get() {
            val missingFields = mutableListOf<FieldError>()

            entryFields.forEach { (fieldId, entry) ->
                if (entry.value.isNullOrBlank()) {
                    missingFields.add(FieldError.FieldRequired(fieldId))
                }
            }

            if (dateTime == null) {
                missingFields.add(FieldError.DateTimeRequired)
            } else if (dateTime.isAfter(LocalDateTime.now())) {
                missingFields.add(FieldError.DateTimeInFuture)
            }

            return missingFields
        }

    sealed class FieldError {
        data class FieldRequired(val fieldId: String) : FieldError()
        object DateTimeRequired : FieldError()
        object DateTimeInFuture : FieldError()

    }
}
