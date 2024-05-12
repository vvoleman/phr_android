package cz.vvoleman.phr.featureMeasurement.ui.mapper.addEditEntry

import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.EntryFieldItem
import cz.vvoleman.phr.featureMeasurement.ui.component.entryField.fields.NumericEntryFieldItem
import cz.vvoleman.phr.featureMeasurement.ui.model.addEditEntry.EntryFieldUiModel
import cz.vvoleman.phr.featureMeasurement.ui.model.core.MeasurementGroupFieldUi
import cz.vvoleman.phr.featureMeasurement.ui.model.core.field.NumericFieldUiModel
import kotlinx.coroutines.CoroutineScope

class EntryFieldUiModelToEntryFieldItemMapper {

    fun toEntryFieldItem(
        entryFields: List<EntryFieldUiModel>,
        fields: List<MeasurementGroupFieldUi>,
        listener: EntryFieldItem.EntryFieldItemListener,
        coroutineScope: CoroutineScope,
    ): List<EntryFieldItem> {
        return entryFields.mapNotNull { entryField ->
            val field = fields.find { it.id == entryField.fieldId }
            when (field) {
                is NumericFieldUiModel -> {
                    NumericEntryFieldItem(
                        label = field.name,
                        value = entryField.value?.toDoubleOrNull() ?: 0.0,
                        minimalValue = field.minimalValue,
                        maximalValue = field.maximalValue,
                        id = field.id,
                        coroutineScope = coroutineScope,
                        listener = listener
                    )
                }

                else -> {
                    null
                }
            }
        }
    }
}
