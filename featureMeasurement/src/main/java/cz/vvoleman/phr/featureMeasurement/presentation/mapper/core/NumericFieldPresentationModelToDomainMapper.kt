package cz.vvoleman.phr.featureMeasurement.presentation.mapper.core

import cz.vvoleman.phr.featureMeasurement.domain.model.core.field.NumericFieldDomainModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.core.field.NumericFieldPresentationModel

class NumericFieldPresentationModelToDomainMapper(
    private val unitGroupMapper: UnitGroupPresentationModelToDomainMapper,
) {

    fun toDomain(model: NumericFieldPresentationModel): NumericFieldDomainModel {
        return NumericFieldDomainModel(
            id = model.id,
            name = model.name,
            unitGroup = unitGroupMapper.toDomain(model.unitGroup!!),
            minimalValue = model.minimalValue,
            maximalValue = model.maximalValue
        )
    }

    fun toPresentation(model: NumericFieldDomainModel): NumericFieldPresentationModel {
        return NumericFieldPresentationModel(
            id = model.id,
            name = model.name,
            unitGroup = unitGroupMapper.toPresentation(model.unitGroup),
            minimalValue = model.minimalValue,
            maximalValue = model.maximalValue
        )
    }
}
