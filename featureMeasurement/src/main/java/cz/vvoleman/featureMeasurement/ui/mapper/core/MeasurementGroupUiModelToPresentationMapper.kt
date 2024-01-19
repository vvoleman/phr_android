package cz.vvoleman.featureMeasurement.ui.mapper.core

import cz.vvoleman.featureMeasurement.domain.model.MeasurementGroupField
import cz.vvoleman.featureMeasurement.presentation.model.core.MeasurementGroupPresentationModel
import cz.vvoleman.featureMeasurement.presentation.model.core.field.NumericFieldPresentationModel
import cz.vvoleman.featureMeasurement.ui.model.core.MeasurementGroupUiModel
import cz.vvoleman.featureMeasurement.ui.model.core.field.NumericFieldUiModel
import cz.vvoleman.phr.common.ui.mapper.patient.PatientUiModelToPresentationMapper

class MeasurementGroupUiModelToPresentationMapper(
    private val scheduleItemMapper: MeasurementGroupScheduleItemUiModelToPresentationMapper,
    private val entryMapper: MeasurementGroupEntryUiModelToPresentationMapper,
    private val numericFieldMapper: NumericFieldUiModelToPresentationMapper,
    private val patientMapper: PatientUiModelToPresentationMapper,
) {

    fun toPresentation(model: MeasurementGroupUiModel): MeasurementGroupPresentationModel {
        return MeasurementGroupPresentationModel(
            id = model.id,
            name = model.name,
            scheduleItems = model.scheduleItems.map { scheduleItemMapper.toPresentation(it) },
            entries = model.entries.map { entryMapper.toPresentation(it) },
            fields = mapFields(model.fields),
            patient = patientMapper.toPresentation(model.patient),
        )
    }

    fun toUi(model: MeasurementGroupPresentationModel): MeasurementGroupUiModel {
        return MeasurementGroupUiModel(
            id = model.id,
            name = model.name,
            scheduleItems = model.scheduleItems.map { scheduleItemMapper.toUi(it) },
            entries = model.entries.map { entryMapper.toUi(it) },
            fields = mapFields(model.fields),
            patient = patientMapper.toUi(model.patient),
        )
    }

    private fun mapFields(list: List<MeasurementGroupField>): List<MeasurementGroupField> {
        return list.map {
            when (it) {
                is NumericFieldUiModel -> numericFieldMapper.toPresentation(it)
                is NumericFieldPresentationModel -> numericFieldMapper.toUi(it)
                else -> {
                    throw IllegalArgumentException("Unknown type of MeasurementGroupField")
                }
            }
        }
    }

}
