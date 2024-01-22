package cz.vvoleman.phr.featureMeasurement.presentation.model.addEdit

import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupFieldPresentation
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.field.unit.UnitGroupPresentationModel
import cz.vvoleman.phr.common.presentation.model.frequencySelector.FrequencyDayPresentationModel
import cz.vvoleman.phr.common.presentation.model.patient.PatientPresentationModel
import java.time.LocalTime

data class AddEditMeasurementViewState(
    val patient: PatientPresentationModel,
    val name: String = "",
    val measurementGroup: MeasurementGroupPresentationModel? = null,
    val times: Set<LocalTime> = emptySet(),
    val frequencyDays: List<FrequencyDayPresentationModel> = emptyList(),
    val frequencyDaysDefault: List<FrequencyDayPresentationModel> = emptyList(),
    val fields: List<MeasurementGroupFieldPresentation> = emptyList(),
    val unitGroups: List<UnitGroupPresentationModel> = emptyList(),
) {

   val missingFields: List<RequiredField>
        get() {
            val missingFields = mutableListOf<RequiredField>()
            if (name.isEmpty()) {
                missingFields.add(RequiredField.NAME)
            }
            if (fields.isEmpty()) {
                missingFields.add(RequiredField.FIELD)
            }
            if (times.isEmpty()) {
                missingFields.add(RequiredField.TIME)
            }
            if (frequencyDays.isEmpty()) {
                missingFields.add(RequiredField.FREQUENCY)
            }
            return missingFields
        }

    enum class RequiredField {
        NAME,
        FIELD,
        TIME,
        FREQUENCY,
    }
}
