package cz.vvoleman.phr.featureMeasurement.presentation.mapper.detail

import cz.vvoleman.phr.featureMeasurement.domain.model.detail.FieldStatsDomainModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.FieldStatsPresentationModel

class FieldStatsPresentationModelToDomainMapper {

    fun toDomain(model: FieldStatsPresentationModel): FieldStatsDomainModel {
        return FieldStatsDomainModel(
            fieldId = model.fieldId,
            name = model.name,
            unit = model.unit,
            values = model.values,
            minValue = model.minValue,
            maxValue = model.maxValue,
            weekAvgValue = model.weekAvgValue,
        )
    }

    fun toPresentation(model: FieldStatsDomainModel): FieldStatsPresentationModel {
        return FieldStatsPresentationModel(
            fieldId = model.fieldId,
            name = model.name,
            unit = model.unit,
            values = model.values,
            minValue = model.minValue,
            maxValue = model.maxValue,
            weekAvgValue = model.weekAvgValue,
        )
    }

    fun toPresentation(models: List<FieldStatsDomainModel>): List<FieldStatsPresentationModel> {
        return models.map { toPresentation(it) }
    }
}
