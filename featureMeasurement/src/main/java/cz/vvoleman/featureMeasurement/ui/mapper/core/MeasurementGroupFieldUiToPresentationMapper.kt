package cz.vvoleman.featureMeasurement.ui.mapper.core

import cz.vvoleman.featureMeasurement.presentation.model.core.MeasurementGroupFieldPresentation
import cz.vvoleman.featureMeasurement.presentation.model.core.field.NumericFieldPresentationModel
import cz.vvoleman.featureMeasurement.ui.model.core.MeasurementGroupFieldUi
import cz.vvoleman.featureMeasurement.ui.model.core.field.NumericFieldUiModel

class MeasurementGroupFieldUiToPresentationMapper(
    private val numericFieldMapper: NumericFieldUiModelToPresentationMapper
) {

    fun toPresentation(model: MeasurementGroupFieldUi): MeasurementGroupFieldPresentation {
        return when (model) {
            is NumericFieldUiModel -> numericFieldMapper.toPresentation(model)
            else -> throw IllegalArgumentException("Unknown field type: ${model::class.java}")
        }
    }

    fun toPresentation(models: List<MeasurementGroupFieldUi>): List<MeasurementGroupFieldPresentation> {
        return models.map { toPresentation(it) }
    }

    fun toUi(model: MeasurementGroupFieldPresentation): MeasurementGroupFieldUi {
        return when (model) {
            is NumericFieldPresentationModel -> numericFieldMapper.toUi(model)
            else -> throw IllegalArgumentException("Unknown field type: ${model::class.java}")
        }
    }

    fun toUi(models: List<MeasurementGroupFieldPresentation>): List<MeasurementGroupFieldUi> {
        return models.map { toUi(it) }
    }

}
